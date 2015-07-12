package org.frenchfrie.chantons.songs;

/**
 * Song object to use for external exchanges as JSon import or export.
 */
public class SongDTO {
    private String title;
    private String author;
    private String lyrics;

    public SongDTO() {
    }

    public SongDTO(String title, String author, String lyrics) {
        this.title = title;
        this.author = author;
        this.lyrics = lyrics;
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
