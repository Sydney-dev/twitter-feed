package org.sydney.twitter.service;

import org.sydney.twitter.domain.Tweet;
import org.sydney.twitter.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;

public class FeedService {

    public static final String TWEET_DELIMITER = ">";
    public static final int TWEET_LENGTH = 140;
    public static final String TWEET_FORMATTER = "\t@%s: %s";
    public static final String TWEET_LENGTH_ERROR = "Tweet should be at most 140 characters, but got [%s] characters";
    private UserService userService;

    public FeedService(UserService userService) {
        this.userService = userService;
    }

    public List<Tweet> loadTweets(String filename) {
        List<Tweet> tweets = new ArrayList<>();
//        Stream<String> lines = FileReader.readFile(filename);
//        lines.forEach(line -> buildTweet(tweets, line));
        return tweets;
    }

    protected void buildTweet(List<Tweet> tweets, String line) {
        String[] splitLine = line.split(TWEET_DELIMITER);
        String text = splitLine[1].trim();
        validateTweetLength(text);
        tweets.add(Tweet.newBuilder()
                .withUser(splitLine[0])
                .withText(text)
                .build());
    }

    private void validateTweetLength(String text) {
        if (text.length() > TWEET_LENGTH) {
            throw new RuntimeException(format(TWEET_LENGTH_ERROR, text.length()));
        }
    }

    public void printTweetFeeds(String userFileName, String tweetFilename) {
        Set<User> users = userService.retrieveUser(userFileName);
        List<Tweet> tweets = this.loadTweets(tweetFilename);

        users.forEach(user -> {
            System.out.println(user.getName());
            printUserTweet(tweets, user);
            user.getFollowings().forEach(following -> printUserTweet(tweets, following));
        });
    }

    private void printUserTweet(List<Tweet> tweets, User user) {
        tweets.forEach(tweet -> {
            if (tweet.getUser().equals(user.getName())) {
                System.out.println(format(TWEET_FORMATTER, tweet.getUser(), tweet.getText()));
            }
        });
    }

}
