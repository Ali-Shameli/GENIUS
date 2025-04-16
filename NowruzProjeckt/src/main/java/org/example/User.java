package org.example;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String username;
    private String password;
    private Role role;
    private Set<String> following; // ذخیره‌ی نام کاربری کاربران فالو شده

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.following = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Role getRole() {
        return role;
    }

    public Set<String> getFollowing() {
        return following;
    }

    public void follow(String artistUsername) {
        if (artistUsername != null && !following.contains(artistUsername)) {
            following.add(artistUsername);
        }
    }

    public boolean isFollowing(String artistUsername) {
        return following.contains(artistUsername);
    }

    public void unfollow(String artistUsername) {
        following.remove(artistUsername);
    }
}