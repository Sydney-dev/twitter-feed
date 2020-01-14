package org.sydney.twitter.service;

import org.sydney.twitter.Tweet;
import org.sydney.twitter.User;

import java.util.*;
import java.util.stream.Stream;

public class FeedService {

    public static final String FOLLOWS_DELIMITER = "follows";
    public static final String COMMA_DELIMITER = ",";
    public static final String TWEET_DELIMITER = ">";
    public static final int TWEET_LENGTH = 140;

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

    public List<Tweet> loadTweets(String filename) {
        List<Tweet> tweets = new ArrayList<>();
        Stream<String> lines = FileReader.readFile(filename);
        lines.forEach(line-> {
            String[] splitLine = line.split(TWEET_DELIMITER);
            String text = splitLine[1].trim();
            validateTweetLength(text);
            tweets.add(Tweet.newBuilder()
                    .withUser(splitLine[0])
                    .withText(text)
                    .build());
        });

        return tweets;
    }

    private void validateTweetLength(String text) {
        if(text.length() > TWEET_LENGTH){
            throw new RuntimeException("Tweet should be at most 140 characters.");
        }
    }
}
