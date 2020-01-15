package org.sydney.twitter.service;

import org.junit.Before;
import org.junit.Test;
import org.sydney.twitter.domain.Tweet;
import org.sydney.twitter.domain.User;

import java.io.PrintStream;
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
        out= mock(PrintStream.class);
        System.setOut(out);
    }

    @Test
    public void shouldPrintTweeterFeeds(){
        feedService.printTweetFeeds("user.txt", "tweet.txt");
        verify(out, atLeastOnce()).println(contains(ALAN));
    }

    @Test
    public void shouldPrintUserTweet(){

        List<Tweet> tweets = Collections.singletonList(Tweet
                .newBuilder().withUser(ALAN).withText("Testing").build());

        feedService.printUserTweet(tweets, new User(ALAN));
        verify(out).println("\t@Alan: Testing");
    }



}