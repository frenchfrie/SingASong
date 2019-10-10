package com.frenchfrie.singasong.songs;

import android.content.ContentValues;
import android.database.Cursor;

import com.frenchfrie.data.RowMapper;

class SongRowMapper implements RowMapper<Song> {

    public static final String SONG_COLUMN_KEY = "id";
    public static final String SONG_COLUMN_TITLE = "title";
    public static final String SONG_COLUMN_DESCRIPTION = "description";
    public static final String SONG_COLUMN_AUTHOR_NAME = "author_name";
    public static final String SONG_COLUMN_RAW_LYRICS = "raw_lyrics";
    public static final String SONG_COLUMN_RECORDING = "recording";

    private CoupletsDAO coupletsDAO;

    public SongRowMapper(CoupletsDAO coupletsDAO) {
        this.coupletsDAO = coupletsDAO;
    }

    @Override
    public Song mapRow(Cursor cursor) {
        Song song = new Song();
        song.setId(cursor.getLong(cursor.getColumnIndex(SONG_COLUMN_KEY)));
        song.setTitle(cursor.getString(cursor.getColumnIndex(SONG_COLUMN_TITLE)));
        song.setDescription(cursor.getString(cursor.getColumnIndex(SONG_COLUMN_DESCRIPTION)));
        song.setAuthor(cursor.getString(cursor.getColumnIndex(SONG_COLUMN_AUTHOR_NAME)));
        song.setRawLyrics(cursor.getString(cursor.getColumnIndex(SONG_COLUMN_RAW_LYRICS)));
        song.setRecordingFileName(cursor.getString(cursor.getColumnIndex(SONG_COLUMN_RECORDING)));
        song.setCouplets(coupletsDAO.findSongCouplets(song.getId()));
        song.setChorus(coupletsDAO.findSongChorus(song.getId()));
        return song;
    }


    public ContentValues fillValues(Song songToSave, ContentValues values) {
        if (values == null) values = new ContentValues();
        values.put(SONG_COLUMN_TITLE, songToSave.getTitle());
        values.put(SONG_COLUMN_AUTHOR_NAME, songToSave.getAuthor());
        values.put(SONG_COLUMN_RAW_LYRICS, songToSave.getRawLyrics());
        return values;
    }
}
