package org.sydney.twitter.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.sydney.twitter.domain.Tweet;
import org.sydney.twitter.domain.User;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;


public class FeedServiceTest {

    public static final String ALAN = "Alan";
    private FeedService feedService;

    private PrintStream out;

    @Before
    public void setUp(){
        FileReader fileReader = new FileReader();
        feedService = new FeedService(
                new UserService(fileReader),
                new TweetService(fileReader)
        );

    }

    @Test
    public void shouldPrintTweeterFeeds(){
        out= mock(PrintStream.class);
        System.setOut(out);
        feedService.printTweetFeeds("user.txt", "tweet.txt");
        verify(out, atLeastOnce()).println(contains(ALAN));
    }

    @Test
    public void shouldAppendUserTweet(){

        List<Tweet> tweets = Collections.singletonList(Tweet
                .newBuilder().withUser(ALAN)
                .withText("Testing")
                .withPriority(1L)
                .build());

        ArrayList<String> feeds = new ArrayList<>();
        feedService.appendTweets(tweets, new User(ALAN), feeds);

        Assertions.assertThat(feeds).hasSize(1)
                .extracting(String::toString)
                .contains("1 \t@Alan: Testing");
    }

    @Test
    public void shouldSimulateTwitterFeed(){
        feedService.printTweetFeeds("user.txt", "tweet.txt");
    }



}