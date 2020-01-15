package org.sydney.twitter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sydney.twitter.domain.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {

    @InjectMocks
    private TweetService tweetService;
    @Mock
    private FileReader fileReader;

    @Test
    public void shouldLoadTweetsFromFile() {
        when(fileReader.readFile(anyString())).thenReturn(findTweets());
        List<Tweet> tweets = tweetService.loadTweets("tweet.txt");

        assertThat(tweets)
                .isNotEmpty()
                .hasSize(3)
                .extracting(Tweet::getUser)
                .contains("Alan");

        verify(fileReader,only()).readFile(anyString());
    }


    @Test
    public void shouldBuildTweet() {

        List<Tweet> tweets = new ArrayList<>();
        String line = "Alan> If you have a procedure with 10 parameters, you probably missed some.";
        final AtomicInteger count = new AtomicInteger();
        tweetService.buildTweet(tweets, line, count);

        assertThat(tweets)
                .hasSize(1)
                .extracting(Tweet::getText)
                .containsExactly("If you have a procedure with 10 parameters, you probably missed some.");
    }

    @Test
    public void shouldFailWhenTweetIsMoreThan140Characters() {
        String expectedErrorMessage = String.format(TweetService.TWEET_LENGTH_ERROR, 145);
        try {
            List<Tweet> tweets = new ArrayList<>();
            String line = "Alan> If you have a procedure with 10 parameters, you probably missed some." +
                    "If you have a procedure with 10 parameters, you probably missed some or two.";
            final AtomicInteger count = new AtomicInteger();
            tweetService.buildTweet(tweets, line, count);
            fail("Expected tweet length exception.");
        } catch (Exception ex) {
            assertThat(ex).hasMessage(expectedErrorMessage);
        }
    }


    private Stream<String> findTweets() {
        return Stream.of(
                "Alan> If you have a procedure with 10 parameters, you probably missed some.",
                "Ward> There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.",
                "Alan> Random numbers should not be generated with a method chosen at random."
        );
    }

}