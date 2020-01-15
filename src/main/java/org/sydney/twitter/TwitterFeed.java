package org.sydney.twitter;

import org.sydney.twitter.service.FeedService;
import org.sydney.twitter.service.FileReader;
import org.sydney.twitter.service.TweetService;
import org.sydney.twitter.service.UserService;

public class TwitterFeed {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        FeedService feedService = new FeedService(
                new UserService(fileReader),
                new TweetService(fileReader));
        feedService.printTweetFeeds("user.txt", "tweet.txt");
    }

}
