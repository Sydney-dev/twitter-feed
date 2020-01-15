package org.sydney.twitter.service;

import org.sydney.twitter.domain.User;

import java.util.*;
import java.util.stream.Stream;

public class UserService {

    public static final String FOLLOWS_DELIMITER = "follows";
    public static final String COMMA_DELIMITER = ",";
    private FileReader fileReader;

    public UserService(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public Set<User> retrieveUser(String fileName) {
        Map<String, User> users = new HashMap<>();
        Stream<String> lines = this.fileReader.readFile(fileName);
        lines.forEach(line -> populateUsers(users, line.split(FOLLOWS_DELIMITER)));
        return new TreeSet<>(users.values());
    }

    private void populateUsers(Map<String, User> users, String[] userNames) {
        String userName = trim(userNames[0]);
        User user = getUser(users, userName);
        populateFollowing(user, userNames[1], users);
    }

    protected void populateFollowing(User user, String followings, Map<String, User> users) {
        Arrays.asList(followings.split(COMMA_DELIMITER)).forEach(followingName -> {
            followingName = trim(followingName);
            User following = new User(followingName);
            user.getFollowings().add(following);
            users.putIfAbsent(followingName, following);
        });
    }

    protected User getUser(Map<String, User> users, String userName) {
        User user = users.get(userName);
        if (user == null) {
            user = new User(userName);
            users.put(userName, user);
        }
        return user;
    }

    private String trim(String name) {
        Objects.requireNonNull(name);
        return name.replace(" ", "");
    }

}
