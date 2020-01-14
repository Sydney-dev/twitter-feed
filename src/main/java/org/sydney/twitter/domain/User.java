package org.sydney.twitter.domain;

import java.util.*;

public class User implements Comparable<User>{
    private String name;
    private Set<User> followings;

    public User(String name) {
        this.name = name;
        this.followings = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<User> getFollowings() {
        return followings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(User user) {
        return this.getName().compareTo(user.getName());
    }
}
