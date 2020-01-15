package org.sydney.twitter;

import org.sydney.twitter.service.FeedService;
import org.sydney.twitter.service.UserService;

public class TwitterFeed {

    public static void main(String[] args) {
        FeedService feedService = new FeedService(new UserService());
        feedService.printTweetFeeds("user.txt", "tweet.txt");
    }

}
