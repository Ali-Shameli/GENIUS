package org.example.userRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.example.User;
import org.example.Song;
import org.example.managers.SongManager;

public class ArtistMenu {
    static Scanner scanner = new Scanner(System.in);
    static SongManager songManager = new SongManager(); // ایجاد شیء از SongManager

    public static void showMenu(User user) {
        while (true) {
            System.out.println("\nArtist Menu:");
            System.out.println("1. Add new song");
            System.out.println("2. View your songs");
            System.out.println("3. Edit a song");
            System.out.println("4. Delete a song");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // پاک کردن بافر

            switch (choice) {
                case 1:
                    addSong(user);
                    break;
                case 2:
                    viewSongs(user);
                    break;
                case 3:
                    editSong(user);
                    break;
                case 4:
                    deleteSong(user);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void addSong(User user) {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter song lyrics: ");
        String lyrics = scanner.nextLine();

        // ایجاد آهنگ جدید و اضافه کردن آن به SongManager
        Song newSong = new Song(title, user.getUsername(), lyrics);
        songManager.addSong(newSong);
        System.out.println("Song added successfully!");
    }

    private static void viewSongs(User user) {
        List<Song> allSongs = songManager.getAllSongs(); // دریافت لیست آهنگ‌ها از SongManager
        List<Song> artistSongs = new ArrayList<>();

        // فیلتر کردن آهنگ‌های هنرمند جاری
        for (Song song : allSongs) {
            if (song.getArtist().equals(user.getUsername())) {
                artistSongs.add(song);
            }
        }

        if (artistSongs.isEmpty()) {
            System.out.println("You have no songs.");
            return;
        }

        System.out.println("\nYour Songs:");

        // نمایش لیست آهنگ‌ها و شماره‌گذاری
        for (int i = 0; i < artistSongs.size(); i++) {
            System.out.println((i + 1) + ". " + artistSongs.get(i).getTitle());
        }

        // درخواست شماره آهنگ برای نمایش جزئیات
        System.out.print("\nEnter the number of the song you want to view: ");
        int songChoice = scanner.nextInt();
        scanner.nextLine();  // پاک کردن بافر

        // بررسی ورودی و نمایش جزئیات آهنگ انتخابی
        if (songChoice < 1 || songChoice > artistSongs.size()) {
            System.out.println("Invalid choice.");
        } else {
            Song selectedSong = artistSongs.get(songChoice - 1);
            // نمایش ویژگی‌های آهنگ
            System.out.println("\nSong Title: " + selectedSong.getTitle());
            System.out.println("Lyrics: " + selectedSong.getLyrics());
            System.out.println("Views: " + selectedSong.getViews());
        }
    }
    private static void editSong(User user) {
        System.out.print("Enter the title of the song you want to edit: ");
        String title = scanner.nextLine();

        // جستجو برای آهنگ
        Song song = songManager.findSongByTitle(title);

        if (song == null || !song.getArtist().equals(user.getUsername())) {
            System.out.println("Song not found or you don't have permission to edit it.");
            return;
        }

        System.out.println("Which part do you want to edit?");
        System.out.println("1. Edit title");
        System.out.println("2. Edit lyrics");
        System.out.println("3. Edit both title and lyrics");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // پاک کردن بافر

        switch (choice) {
            case 1:
                System.out.print("Enter the new title: ");
                String newTitle = scanner.nextLine();
                song.setTitle(newTitle);
                System.out.println("Title updated successfully!");
                break;
            case 2:
                System.out.print("Enter the new lyrics: ");
                String newLyrics = scanner.nextLine();
                song.setLyrics(newLyrics);
                System.out.println("Lyrics updated successfully!");
                break;
            case 3:
                System.out.print("Enter the new title: ");
                newTitle = scanner.nextLine();
                song.setTitle(newTitle);
                System.out.print("Enter the new lyrics: ");
                newLyrics = scanner.nextLine();
                song.setLyrics(newLyrics);
                System.out.println("Title and lyrics updated successfully!");
                break;
            default:
                System.out.println("Invalid option!");
        }

        songManager.saveSongs(); // ذخیره تغییرات
    }

    private static void deleteSong(User user) {
        System.out.print("Enter the title of the song you want to delete: ");
        String title = scanner.nextLine();

        // چک کنیم که این آهنگ متعلق به همین یوزر است
        Song song = songManager.findSongByTitle(title);
        if (song == null || !song.getArtist().equals(user.getUsername())) {
            System.out.println("Song not found or you don't have permission to delete this song.");
            return;
        }
        if (songManager.deleteSong(title)) {
            System.out.println("Song deleted successfully!");
        } else {
            System.out.println("Failed to delete song.");
        }
    }
}