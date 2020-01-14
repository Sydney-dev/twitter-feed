package org.sydney.twitter.service;

import org.junit.Before;
import org.junit.Test;
import org.sydney.twitter.Tweet;
import org.sydney.twitter.User;

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
    public void shouldGetExistingUser() {

        String userName = "Sydney";
        String userName2 = "Chauke";

        Map<String, User> users = new HashMap<>();
        users.put(userName, createUser(userName));
        users.put(userName2, createUser(userName2));

        User user = feedService.getUser(users, userName);

        assertEquals(user.getName(), userName);
        assertTrue(users.size() == 2);
    }

    @Test
    public void shouldCreateUserIfDoesNotExist() {

        String userName = "Sydney";
        String userName2 = "Chauke";

        Map<String, User> users = new HashMap<>();
        users.put(userName2, createUser(userName2));

        assertTrue(users.size() == 1);

        User user = feedService.getUser(users, userName);

        assertEquals(user.getName(), userName);
        assertTrue(users.size() == 2);
    }

    @Test
    public void shouldLoadUsersFromFile() {
        Set<User> users = feedService.retrieveUser("user.txt");

        assertTrue(users.size() > 0);
        assertTrue(users.stream().anyMatch(user -> "Ward".equals(user.getName())));
    }

    @Test
    public void shouldFindTweetsByUsername(){

        List<Tweet> tweets = feedService.findTweetsByUserName("Alan");

        assertTrue(tweets.size() > 0);
    }

    private User createUser(String userName) {
        return new User(userName);
    }


}