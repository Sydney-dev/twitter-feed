package org.sydney.twitter.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.sydney.twitter.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    public static final String USER_NAME_1 = "Sydney";
    public static final String USER_NAME_2 = "Chauke";
    @InjectMocks
    private UserService userService;

    @Mock
    private FileReader fileReader;

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
        when(fileReader.readFile(anyString())).thenReturn(getUsers());
        Set<User> users = userService.retrieveUser("user.txt");

        assertThat(users)
                .hasSize(3)
                .extracting(User::getName)
                .contains("Ward");
        verify(fileReader, only()).readFile(anyString());
    }

    private Stream<String> getUsers() {
        return Stream.of(
                "Ward follows Alan",
                "Alan follows Martin",
                "Ward follows Martin, Alan");
    }


    private User createUser(String userName) {
        return new User(userName);
    }
}