package org.example;

import org.example.userRole.AdminMenu;
import org.example.userRole.ArtistMenu;
import org.example.userRole.UserMenu;
import org.example.managers.UserManager;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Role> roles = new ArrayList<>();
    static UserManager userManager = new UserManager(); // ایجاد شیء از UserManager

    public static void main(String[] args) {
        // تعریف نقش‌ها
        if (roles.isEmpty()) {
            roles.add(new Role("Regular User"));
            roles.add(new Role("Artist"));
            roles.add(new Role("Admin"));
        }

        userManager.loadUsers(); // لود کاربران از فایل
        userManager.loadFollowers();
        while (true) {
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // پاک کردن بافر

            if (choice == 1) {
                signUp();
            } else if (choice == 2) {
                login();
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    static void signUp() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (userManager.getUser(username) != null) { // بررسی وجود کاربر
            System.out.println("This username is already taken!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Select user role:");
        for (int i = 0; i < roles.size() - 1; i++) {
            System.out.println((i + 1) + ". " + roles.get(i).getName());
        }
        System.out.print("Please choose a role: ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // پاک کردن بافر

        if (roleChoice < 1 || roleChoice > roles.size() - 1) {
            System.out.println("Invalid role! Defaulting to 'Regular User'.");
            roleChoice = 1;
        }

        Role role = roles.get(roleChoice - 1);
        User newUser = new User(username, password, role);
        userManager.addUser(newUser); // اضافه کردن کاربر
        userManager.saveUsers(); // ذخیره کاربران در فایل
        System.out.println("Registration successful!");
    }

    static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        User user = userManager.getUser(username);

        if (user == null) {
            System.out.println("Username does not exist!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (user.checkPassword(password)) {
            System.out.println("Login successful!");
            System.out.println("Your role: " + user.getRole().getName());

            switch (user.getRole().getName()) {
                case "Regular User":
                    UserMenu.showMenu(user);
                    break;
                case "Artist":
                    ArtistMenu.showMenu(user);
                    break;
                case "Admin":
                    AdminMenu.showMenu(user);
                    break;
            }
        } else {
            System.out.println("Incorrect password!");
        }
    }
}