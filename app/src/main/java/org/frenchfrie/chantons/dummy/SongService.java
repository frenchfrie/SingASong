package org.frenchfrie.chantons.dummy;

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

    /**
     * An array of sample (dummy) items.
     */
    public static List<Song> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Song> ITEM_MAP = new HashMap<>();

    static {
        // Add 3 sample items.
        addItem(new Song("1", "Item 1"));
        addItem(new Song("2", "Item 2"));
        addItem(new Song("3", "Item 3"));
    }

    private static void addItem(Song item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Song {
        public String id;
        public String content;

        public Song(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
