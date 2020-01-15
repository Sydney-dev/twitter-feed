package org.sydney.twitter.service;

import org.junit.Before;
import org.junit.Test;
import org.sydney.twitter.domain.Tweet;
import org.sydney.twitter.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class FeedServiceTest {


    private FeedService feedService;

    @Before
    public void setUp() {
        feedService = new FeedService();
    }



    @Test
    public void shouldLoadTweetsFromFile() {
        List<Tweet> tweets = feedService.loadTweets("tweet.txt");
        assertTrue(tweets.size() > 0);
    }


}