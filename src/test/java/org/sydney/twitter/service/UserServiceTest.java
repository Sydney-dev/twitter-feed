package org.sydney.twitter.service;

import org.junit.Before;
import org.junit.Test;
import org.sydney.twitter.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    public static final String USER_NAME_1 = "Sydney";
    public static final String USER_NAME_2 = "Chauke";
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void shouldGetExistingUser() {

        Map<String, User> users = new HashMap<>();
        users.put(USER_NAME_1, createUser(USER_NAME_1));
        users.put(USER_NAME_2, createUser(USER_NAME_2));

        User user = userService.getUser(users, USER_NAME_1);

        assertThat(user.getName()).isEqualTo(USER_NAME_1);
        assertThat(users).hasSize(2);
    }

    @Test
    public void shouldCreateUserIfDoesNotExist() {
        Map<String, User> users = new HashMap<>();
        users.put(USER_NAME_2, createUser(USER_NAME_2));

        assertThat(users).hasSize(1);

        User user = userService.getUser(users, USER_NAME_1);

        assertThat(user.getName()).isEqualTo(USER_NAME_1);
        assertThat(users).hasSize(2);
    }

    @Test
    public void shouldLoadUsersFromFile() {
        Set<User> users = userService.retrieveUser("user.txt");

        assertThat(users)
                .hasSize(3)
                .extracting(User::getName)
                .contains("Ward");
    }


    private User createUser(String userName) {
        return new User(userName);
    }
}