package org.example.userRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.Main;
import org.example.User;
import org.example.Song;
import org.example.managers.SongManager;
import org.example.managers.UserManager;

public class UserMenu {
    static Scanner scanner = new Scanner(System.in);
    static SongManager songManager = new SongManager(); // برای دسترسی به آهنگ‌ها از SongManager استفاده می‌کنیم
    static UserManager userManager = new UserManager(); // باید به UserManager دسترسی داشته باشیم

    public static void showMenu(User user) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View songs");
            System.out.println("2. Search for songs");
            System.out.println("3. View your favorites");
            System.out.println("4. View suggestions");
            System.out.println("5. Follow an artist");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // پاک کردن بافر ورودی

            switch (choice) {
                case 1:
                    viewSongs();
                    break;
                case 2:
                    searchSongs();
                    break;
                case 3:
                    viewFavorites();
                    break;
                case 4:
                    viewSuggestions();
                    break;
                case 5:
                    followArtist(user); // تغییر استفاده از متد followArtist
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // نمایش تمام آهنگ‌ها برای یوزر
    private static void viewSongs() {
        System.out.println("How would you like to view the songs?");
        System.out.println("1. View all songs");
        System.out.println("2. View most popular songs");
        System.out.println("3. View newest songs");
        System.out.println("4. Search for songs");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // پاک کردن بافر ورودی

        List<Song> allSongs = songManager.getAllSongs(); // گرفتن همه آهنگ‌ها از SongManager
        List<Song> filteredSongs = new ArrayList<>();

        // اعمال فیلترهای مختلف
        switch (choice) {
            case 1:
                filteredSongs = allSongs; // نمایش همه آهنگ‌ها
                break;
            case 2:
                filteredSongs = getMostPopularSongs(allSongs); // محبوب‌ترین‌ها
                break;
            case 3:
                filteredSongs = getNewestSongs(allSongs); // جدیدترین‌ها
                break;
            case 4:
                searchSongs();
                return;
            default:
                System.out.println("Invalid option!");
                return;
        }

        if (filteredSongs.isEmpty()) {
            System.out.println("No songs available.");
            return;
        }

        // نمایش آهنگ‌ها برای کاربر
        System.out.println("\nList of Songs:");
        for (Song song : filteredSongs) {
            System.out.println("- " + song.getTitle() + " (by " + song.getArtist() + ")");
        }

        System.out.print("Choose a song to view details: ");
        String songTitle = scanner.nextLine();

        // نمایش مشخصات آهنگ انتخابی
        Song selectedSong = findSongByTitle(songTitle, filteredSongs);
        if (selectedSong != null) {
            selectedSong.displayInfo();
            selectedSong.incrementViews(); // افزایش تعداد بازدید
            songManager.saveSongs(); // ذخیره تغییرات
        } else {
            System.out.println("Song not found.");
        }
    }// جستجوی آهنگ‌ها بر اساس عنوان یا هنرمند
    private static void searchSongs() {
        System.out.print("Enter song title or artist to search: ");
        String query = scanner.nextLine();
        List<Song> allSongs = songManager.getAllSongs(); // دریافت تمام آهنگ‌ها
        boolean found = false;
        for (Song song : allSongs) {
            if (song.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    song.getArtist().toLowerCase().contains(query.toLowerCase())) {
                song.displayInfo();
                song.incrementViews();
                songManager.saveSongs();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No songs found for your search.");
        }
    }

    // نمایش آهنگ‌های مورد علاقه کاربر
    private static void viewFavorites() {
        System.out.println("Displaying your favorite songs... (This feature will be implemented later)");
    }

    // نمایش پیشنهادات برای کاربر
    private static void viewSuggestions() {
        System.out.println("Displaying song suggestions... (This feature will be implemented later)");
    }

    // پیدا کردن آهنگ با عنوان مشخص
    private static Song findSongByTitle(String title, List<Song> songs) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }

    // فیلتر کردن آهنگ‌ها بر اساس محبوبیت (بر اساس تعداد بازدید)
    private static List<Song> getMostPopularSongs(List<Song> allSongs) {
        // اینجا می‌تونید آهنگ‌ها رو بر اساس تعداد بازدید مرتب کنید
        allSongs.sort((s1, s2) -> Integer.compare(s2.getViews(), s1.getViews()));
        return allSongs;
    }

    // فیلتر کردن آهنگ‌ها بر اساس جدیدترین‌ها (شما می‌تونید این رو با تاریخ اضافه کنید)
    private static List<Song> getNewestSongs(List<Song> allSongs) {
        // اینجا می‌تونید آهنگ‌ها رو بر اساس تاریخ مرتب کنید
        return allSongs; // در حال حاضر فقط لیست همه آهنگ‌ها رو برمی‌گردونه
    }

    private static void followArtist(User user ) {
        userManager.loadUsers();
        System.out.print("Enter artist username to follow: ");
        String artistName = scanner.nextLine();
        // اگر هنرمند موجود بود، کاربر می‌تواند فالو کند
        User artist =userManager.getUser(artistName);

        if (artist != null) {
            // حالا فالو کردن هنرمند
            userManager.followArtist(user.getUsername(), artistName);
            System.out.println("You are now following " + artistName + "!");
        } else {
            System.out.println("Artist not found.");
        }
    }
}