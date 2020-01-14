package org.sydney.twitter;

import org.sydney.twitter.domain.Tweet;
import org.sydney.twitter.domain.User;
import org.sydney.twitter.service.FeedService;

import java.util.List;
import java.util.Set;

public class TwitterFeed {

    public static void main(String[] args) {

        FeedService feedService = new FeedService();

        Set<User> users = feedService.retrieveUser("user.txt");
        List<Tweet> tweets = feedService.loadTweets("tweet.txt");

        users.forEach(user ->{
            System.out.println(user.getName());
            print(tweets, user);
            user.getFollowings().forEach(following -> print(tweets,following));
        });
    }

    private static void print(List<Tweet> tweets, User user) {
        tweets.forEach(tweet->{
            if(tweet.getUser().equals(user.getName())){
                System.out.println(String.format("\t@%s: %s", tweet.getUser(), tweet.getText()));
            }
        });
    }
}
