package org.frenchfrie.chantons.dummy;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SongService {

    private static List<Song> ITEMS = new ArrayList<>();

    static {
        // Add 3 sample items.
        addItem(new Song(1, "chanson 1", "auteur 1", "paroles 1"));
        addItem(new Song(2, "chanson 2", "auteur 2", "paroles 2"));
        addItem(new Song(3, "chanson 3", "auteur 3", "paroles 3"));
    }

    private static void addItem(Song item) {
        ITEMS.add(item);
    }

    private final ContentResolver contentResolver;

    public SongService(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public List<Song> findAll() {
        return ITEMS;
    }

    public Song findOne(int id) {
        Song foundSong = null;
        for (Song song : ITEMS) {
            if (id == song.getId()) {
                foundSong = song;
                break;
            }
        }
        return foundSong;
    }

    public static class Song {
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
}
