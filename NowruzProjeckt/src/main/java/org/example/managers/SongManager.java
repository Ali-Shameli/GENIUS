package org.example.managers;

import org.example.Song;
import java.io.*;
import java.util.*;

public class SongManager {
    private static final String SONGS_FILE = "songs.csv"; // استفاده از CSV
    private List<Song> songs = new ArrayList<>();

    public SongManager() {
        loadSongs(); // بارگذاری آهنگ‌ها از فایل CSV
    }

    public void addSong(Song song) {
        songs.add(song);
        saveSongs(); // ذخیره آهنگ جدید در فایل CSV
    }

    public List<Song> getAllSongs() {
        return songs; // گرفتن همه آهنگ‌ها
    }

    public Song findSongByTitle(String title) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }

    public boolean editSong(String oldTitle, String newTitle, String newLyrics) {
        Song song = findSongByTitle(oldTitle);
        if (song != null) {
            song.setTitle(newTitle);
            song.setLyrics(newLyrics);
            saveSongs(); // ذخیره تغییرات
            return true;
        }
        return false;
    }

    public boolean deleteSong(String title) {
        Iterator<Song> iterator = songs.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            if (song.getTitle().equalsIgnoreCase(title)) {
                iterator.remove();
                saveSongs(); // ذخیره تغییرات
                return true;
            }
        }
        return false;
    }

    public void saveSongs() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("songs.csv"))) {
            for (Song song : songs) {
                // ذخیره‌سازی به فرمت CSV
                writer.println(song.getTitle() + "," + song.getArtist() + "," + song.getLyrics().replace("\n", " | ") + "," + song.getViews());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSongs() {
        try (BufferedReader reader = new BufferedReader(new FileReader("songs.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String title = parts[0];
                    String artist = parts[1];
                    String lyrics = parts[2].replace(" | ", "\n");
                    int views = Integer.parseInt(parts[3]);
                    Song song = new Song(title, artist, lyrics);
                    for (int i = 0; i < views; i++) {
                        song.incrementViews();
                    }
                    songs.add(song);
                }
            }
        } catch (IOException e) {
            System.out.println("No songs found, starting fresh...");
        }
    }
}