package org.sydney.twitter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.sydney.twitter.domain.Tweet;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(MockitoJUnitRunner.class)
public class FeedServiceTest {

    @InjectMocks
    private FeedService feedService;

    @Test
    public void shouldLoadTweetsFromFile() {
        List<Tweet> tweets = feedService.loadTweets("tweet.txt");
        assertThat(tweets)
                .isNotEmpty()
                .hasSize(3)
                .extracting(Tweet::getUser)
                .contains("Alan");
    }

    @Test
    public void shouldBuildTweet() {

        List<Tweet> tweets = new ArrayList<>();
        String line = "Alan> If you have a procedure with 10 parameters, you probably missed some.";
        feedService.buildTweet(tweets, line);

        assertThat(tweets)
                .hasSize(1)
                .extracting(Tweet::getText)
                .containsExactly("If you have a procedure with 10 parameters, you probably missed some.");
    }

    @Test
    public void shouldFailWhenTweetIsMoreThan140Characters() {
        String expectedErrorMessage = String.format(FeedService.TWEET_LENGTH_ERROR, 145);
        try {
            List<Tweet> tweets = new ArrayList<>();
            String line = "Alan> If you have a procedure with 10 parameters, you probably missed some." +
                    "If you have a procedure with 10 parameters, you probably missed some or two.";
            feedService.buildTweet(tweets, line);
            fail("Expected tweet length exception.");
        } catch (Exception ex){
            assertThat(ex).hasMessage(expectedErrorMessage);
        }
    }



}