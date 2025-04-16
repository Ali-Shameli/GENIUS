package org.example.managers;

import org.example.User;
import org.example.Role;

import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, User> users;  // ذخیره‌سازی کاربران
    private Map<String, Set<String>> followers;  // ذخیره‌سازی فالوئرها (کلید: هنرمند، مقدار: Set از کاربران فالو کننده)

    private static final String USERS_FILE = "users.csv";
    private static final String FOLLOWERS_FILE = "followers.csv";

    public UserManager() {
        users = new HashMap<>();
        followers = new HashMap<>();
//        loadUsers();
        loadFollowers();
    }

    // اضافه کردن کاربر جدید
    public void addUser(User user) {
        users.put(user.getUsername(), user);
        followers.putIfAbsent(user.getUsername(), new HashSet<>());
//        saveUsers();
    }
    public User getUser(String username) {
//        System.out.println("Looking fo user: " + username);
//        System.out.println("Users in mmemory: " + users.keySet());
        return users.get(username);
    }

    // فالو کردن یک هنرمند توسط یک کاربر
    public boolean followArtist(String username, String artistName) {
        if (!users.containsKey(username) || !users.containsKey(artistName)) {
            System.out.println("User or artist not found!");
            return false; // کاربر یا هنرمند وجود ندارد
        }
        if (isFollowing(username, artistName)) {
            System.out.println("You are already following " + artistName + "!");
            return false ;   //کاریر قبلا فالو کرده است
        }
        followers.putIfAbsent(artistName, new HashSet<>());
        boolean added = followers.get(artistName).add(username);
        if (added) {
            saveFollowers();
            System.out.println("You are now following" + artistName + "!");
        }
        return added;
    }

    // آنفالو کردن یک هنرمند
    public boolean unfollowArtist(String username, String artistName) {
        if (followers.containsKey(artistName) && followers.get(artistName).remove(username)) {
            saveFollowers();
            return true;
        }
        System.out.println("You are not following " + artistName + ".");
        return false;
    }

    // نمایش فالوئرهای یک هنرمند
    public Set<String> getFollowers(String artistName) {
        return followers.getOrDefault(artistName, new HashSet<>());
    }

    // بررسی اینکه آیا یک کاربر یک هنرمند خاص را فالو کرده است یا نه
    public boolean isFollowing(String username, String artistName) {
        return followers.getOrDefault(artistName, new HashSet<>()).contains(username);
    }
    // ذخیره کاربران در فایل CSV
    public  void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.csv"))) {
            for (User user : users.values()) {
                writer.println(user.getUsername() + "," + user.getPassword() + "," + user.getRole().getName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ذخیره فالوئرها در فایل CSV
    public void saveFollowers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("followers.csv"))) {
            for (Map.Entry<String, Set<String>> entry : followers.entrySet()) {
                String artistName = entry.getKey();
                for (String follower : entry.getValue()) {
                    writer.write(follower + "," + artistName + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving followers: " + e.getMessage());
        }
    }

    // بارگذاری کاربران از فایل CSV
    public void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String username = data[0];
                    String password = data[1];
                    Role role = null;
                    try {
                        role = Role.valueOf(data[2]);
                    } catch (IllegalArgumentException e) {
                        // اگر نقش نامعتبر بود، پیش‌فرض "Regular User" قرار داده می‌شود
                        System.out.println("Invalid role found for user " + username + ". Assigning default role 'Regular User'.");
                        role = new Role("Regular User");
                    }
                    users.put(username, new User(username, password, role));
                    followers.putIfAbsent(username, new HashSet<>());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Users file not found, starting fresh.");
        }
//        System.out.println("loaded users: " + users.keySet());

    }
    // بارگذاری فالوئرها از فایل CSV
    public void loadFollowers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("followers.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String follower = data[0];
                    String artist = data[1];
                    followers.putIfAbsent(artist, new HashSet<>());
                    followers.get(artist).add(follower);
                }
            }
        } catch (IOException e) {
            System.out.println("Followers file not found, starting fresh.");
        }
//        System.out.println("loaded follow users: " + followers.values());
    }

}