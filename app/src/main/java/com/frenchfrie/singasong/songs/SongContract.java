package com.frenchfrie.singasong.songs;

import android.provider.BaseColumns;

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
            "CREATE TABLE " + SongEntry.TABLE_NAME + " ("
                    + SongEntry.COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + SongEntry.COLUMN_TITLE + " TEXT, "
                    + SongEntry.COLUMN_DESCRIPTION + " TEXT, "
                    + SongEntry.COLUMN_AUTHOR_NAME + " TEXT, "
                    + SongEntry.COLUMN_RAW_LYRICS + " TEXT, "
                    + SongEntry.COLUMN_RECORDING + " TEXT"
                    + ");";


}
