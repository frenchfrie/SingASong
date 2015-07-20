package org.frenchfrie.chantons.songs;

import java.util.List;

/**
 * Song object to use for external exchanges as JSon import or export.
 */
public class SongDTO {
    private String title;
    private String author;
    private String rawLyrics;
    private List<CoupletDTO> couplets;

    public SongDTO() {
    }

    public SongDTO(String title, String author, String rawLyrics) {
        this.title = title;
        this.author = author;
        this.rawLyrics = rawLyrics;
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

    public String getRawLyrics() {
        return rawLyrics;
    }

    public void setRawLyrics(String rawLyrics) {
        this.rawLyrics = rawLyrics;
    }

    public static class CoupletDTO {
        private String verses;
        private String image;

        public String getVerses() {
            return verses;
        }

        public void setVerses(String verses) {
            this.verses = verses;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
