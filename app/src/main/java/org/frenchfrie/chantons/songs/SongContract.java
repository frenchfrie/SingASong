package org.frenchfrie.chantons.songs;

import android.provider.BaseColumns;

import static org.frenchfrie.chantons.songs.SongContract.SongEntry.COLUMN_AUTHOR_NAME;
import static org.frenchfrie.chantons.songs.SongContract.SongEntry.COLUMN_DESCRIPTION;
import static org.frenchfrie.chantons.songs.SongContract.SongEntry.COLUMN_KEY;
import static org.frenchfrie.chantons.songs.SongContract.SongEntry.COLUMN_RAW_LYRICS;
import static org.frenchfrie.chantons.songs.SongContract.SongEntry.COLUMN_RECORDING;
import static org.frenchfrie.chantons.songs.SongContract.SongEntry.COLUMN_TITLE;
import static org.frenchfrie.chantons.songs.SongContract.SongEntry.TABLE_NAME;

public class SongContract {

    private SongContract() {
    }

    public static class SongEntry implements BaseColumns {
        public static final String TABLE_NAME = "songs";
        public static final String COLUMN_KEY = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AUTHOR_NAME = "author_name";
        public static final String COLUMN_RAW_LYRICS = "raw_lyrics";
        public static final String COLUMN_RECORDING = "recording";

    }

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + COLUMN_TITLE + " TEXT, "
                    + COLUMN_DESCRIPTION + " TEXT, "
                    + COLUMN_AUTHOR_NAME + " TEXT, "
                    + COLUMN_RAW_LYRICS + " TEXT, "
                    + COLUMN_RECORDING + " TEXT"
                    + ");";


}
