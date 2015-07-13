package org.frenchfrie.chantons.songs;

import android.database.Cursor;

import org.frenchfrie.sql_support.RowMapper;

class SongRowMapper implements RowMapper<Song> {

    @Override
    public Song mapRow(Cursor cursor) {
        Song song = new Song();
        song.setId(cursor.getInt(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_KEY)));
        song.setTitle(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_TITLE)));
        song.setAuthor(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_AUTHOR_NAME)));
        song.setLyrics(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_LYRICS)));
        return song;
    }
}
