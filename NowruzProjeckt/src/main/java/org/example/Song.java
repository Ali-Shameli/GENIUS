package org.example;

public class Song {
    private String title;
    private String artist;
    private String lyrics;
    private int views;

    public Song(String title, String artist, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        this.views = 0;
    }

    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Artist: " + artist);
        System.out.println("Lyrics: " + lyrics);
        System.out.println("Views: " + views);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getViews() {
        return views;
    }

    public void incrementViews() {
        this.views++;
    }

}