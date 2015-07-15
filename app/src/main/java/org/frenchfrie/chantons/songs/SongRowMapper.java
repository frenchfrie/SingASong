package org.frenchfrie.chantons.songs;

import android.database.Cursor;

import org.frenchfrie.sql_support.RowMapper;

class SongRowMapper implements RowMapper<Song> {

    private CoupletsDAO coupletsDAO;

    public SongRowMapper(CoupletsDAO coupletsDAO) {
        this.coupletsDAO = coupletsDAO;
    }

    @Override
    public Song mapRow(Cursor cursor) {
        Song song = new Song();
        song.setId(cursor.getLong(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_KEY)));
        song.setTitle(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_TITLE)));
        song.setDescription(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_DESCRIPTION)));
        song.setAuthor(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_AUTHOR_NAME)));
        song.setRawLyrics(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_RAW_LYRICS)));
        song.setRecordingFileName(cursor.getString(cursor.getColumnIndex(SongsDAO.SONG_COLUMN_RECORDING)));

        return song;
    }
}
