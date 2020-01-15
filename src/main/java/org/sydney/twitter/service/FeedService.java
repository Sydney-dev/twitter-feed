package org.sydney.twitter.service;

import org.sydney.twitter.domain.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FeedService {

    public static final String TWEET_DELIMITER = ">";
    public static final int TWEET_LENGTH = 140;

    public List<Tweet> loadTweets(String filename) {
        List<Tweet> tweets = new ArrayList<>();
        Stream<String> lines = FileReader.readFile(filename);
        lines.forEach(line-> {
            String[] splitLine = line.split(TWEET_DELIMITER);
            String text = splitLine[1].trim();
            validateTweetLength(text);
            tweets.add(Tweet.newBuilder()
                    .withUser(splitLine[0])
                    .withText(text)
                    .build());
        });

        return tweets;
    }

    private void validateTweetLength(String text) {
        if(text.length() > TWEET_LENGTH){
            throw new RuntimeException("Tweet should be at most 140 characters.");
        }
    }
}
