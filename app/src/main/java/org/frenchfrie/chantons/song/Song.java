package org.frenchfrie.chantons.song;

public class Song {
    private int id;
    private String title;
    private String author;
    private String lyrics;

    public Song() {
    }

    public Song(int id, String title, String author, String lyrics) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.lyrics = lyrics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
