package org.sydney.twitter.service;

import org.sydney.twitter.Tweet;
import org.sydney.twitter.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedService {

    public static final String FOLLOWS_DELIMITER = "follows";
    public static final String COMMA_DELIMITER = ",";
    private List<String> tweets;

    public Set<User> retrieveUser(String fileName) {
        Map<String, User> users = new HashMap<>();
        Stream<String> lines = FileReader.readFile(fileName);
        lines.forEach(line -> populateUsers(users, line.split(FOLLOWS_DELIMITER)));
        return new TreeSet<>(users.values());
    }

    private void populateUsers(Map<String, User> users, String[] userNames) {
        String userName = trim(userNames[0]);
        User user = getUser(users, userName);

        Arrays.asList(userNames[1].split(COMMA_DELIMITER)).forEach(followingName -> {
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

    public List<Tweet> findTweetsByUserName(String username) {
        List<Tweet> tweets = new ArrayList<>();

        getTweets().forEach(line-> {
            String[] splitLine = line.split(">");
            tweets.add(Tweet.newBuilder()
                    .withUser(splitLine[0])
                    .withText(splitLine[1])
                    .build());
        });

        return tweets;
    }

    public List<String> getTweets() {
        if (this.tweets == null) {
            tweets = FileReader.readFile("tweet.txt")
                    .collect(Collectors.toList());
        }
        return tweets;
    }
}
