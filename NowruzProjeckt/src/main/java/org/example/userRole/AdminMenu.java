package org.example.userRole;

import java.util.Scanner;
import org.example.User ;

public class AdminMenu {
    static Scanner scanner = new Scanner(System.in);

    public static void showMenu(User user) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage users");
            System.out.println("2. Manage songs");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Managing users...");
                    break;
                case 2:
                    System.out.println("Managing songs...");
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}