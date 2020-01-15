package org.sydney.twitter.service;

import org.sydney.twitter.domain.Tweet;
import org.sydney.twitter.domain.User;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;

public class FeedService {

    public static final String TWEET_FORMATTER = "\t@%s: %s";

    private UserService userService;
    private TweetService tweetService;

    public FeedService(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    public void printTweetFeeds(String userFileName, String tweetFilename) {
        Set<User> users = userService.retrieveUser(userFileName);
        List<Tweet> tweets = tweetService.loadTweets(tweetFilename);

        users.forEach(user -> {
            System.out.println(user.getName());
            printUserTweet(tweets, user);
            user.getFollowings().forEach(following -> printUserTweet(tweets, following));
        });
    }

    protected void printUserTweet(List<Tweet> tweets, User user) {
        Objects.requireNonNull(user, "User is mandatory.");
        tweets.forEach(tweet -> {
            if (tweet.getUser().equals(user.getName())) {
                System.out.println(format(TWEET_FORMATTER, tweet.getUser(), tweet.getText()));
            }
        });
    }
}