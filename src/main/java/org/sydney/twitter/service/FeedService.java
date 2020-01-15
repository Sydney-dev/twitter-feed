package org.sydney.twitter.service;

import org.sydney.twitter.domain.Tweet;
import org.sydney.twitter.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;

public class FeedService {

    public static final String TWEET_FORMATTER = "%s \t@%s: %s";

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
            List<String> feeds = new ArrayList<>();
            appendTweets(tweets, user, feeds);
            user.getFollowings().forEach(following -> appendTweets(tweets, following, feeds));

            feeds.stream()
                    .sorted()
                    .map(line -> line.substring(line.indexOf("\t")))
                    .forEach(System.out::println);
        });
    }

    protected void appendTweets(List<Tweet> tweets, User user, List<String> feeds) {
        Objects.requireNonNull(user, "User is mandatory.");
        tweets.forEach(tweet -> {
            if (tweet.getUser().equals(user.getName())) {
                feeds.add(format(TWEET_FORMATTER, tweet.getPriority(), tweet.getUser(), tweet.getText()));
            }
        });
    }
}