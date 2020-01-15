package org.sydney.twitter.service;

import org.sydney.twitter.domain.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.lang.String.format;

public class TweetService {

    public static final String TWEET_DELIMITER = ">";
    public static final int TWEET_LENGTH = 140;
    public static final String TWEET_LENGTH_ERROR = "Tweet should be at most 140 characters, but got [%s] characters";
    private FileReader fileReader;

    public TweetService(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public List<Tweet> loadTweets(String filename) {
        List<Tweet> tweets = new ArrayList<>();
        Stream<String> lines = fileReader.readFile(filename);
        final AtomicInteger count = new AtomicInteger();
        lines.forEach(line -> buildTweet(tweets, line, count));
        return tweets;
    }

    protected void buildTweet(List<Tweet> tweets, String line, AtomicInteger count) {
        String[] splitLine = line.split(TWEET_DELIMITER);
        String text = splitLine[1].trim();
        validateTweetLength(text);
        int priority = count.getAndIncrement();
        tweets.add(Tweet.newBuilder()
                .withUser(splitLine[0])
                .withText(text)
                .withPriority(Long.valueOf(priority))
                .build());
    }

    private void validateTweetLength(String text) {
        if (text.length() > TWEET_LENGTH) {
            throw new RuntimeException(format(TWEET_LENGTH_ERROR, text.length()));
        }
    }
}
