package org.sydney.twitter.service;

import org.junit.Before;
import org.junit.Test;
import org.sydney.twitter.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void shouldGetExistingUser() {
        String userName = "Sydney";
        String userName2 = "Chauke";

        Map<String, User> users = new HashMap<>();
        users.put(userName, createUser(userName));
        users.put(userName2, createUser(userName2));

        User user = userService.getUser(users, userName);

        assertEquals(user.getName(), userName);
        assertEquals(users.size(), 2);
    }

    @Test
    public void shouldCreateUserIfDoesNotExist() {

        String userName = "Sydney";
        String userName2 = "Chauke";

        Map<String, User> users = new HashMap<>();
        users.put(userName2, createUser(userName2));

        assertEquals(users.size(), 1);

        User user = userService.getUser(users, userName);

        assertEquals(user.getName(), userName);
        assertEquals(2, users.size());
    }

    @Test
    public void shouldLoadUsersFromFile() {
        Set<User> users = userService.retrieveUser("user.txt");

        assertTrue(users.size() > 0);
        assertTrue(users.stream().anyMatch(user -> "Ward".equals(user.getName())));
    }


    private User createUser(String userName) {
        return new User(userName);
    }
}