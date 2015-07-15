package org.frenchfrie.chantons.songs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.frenchfrie.sql_support.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class SongsDAO extends SQLiteOpenHelper implements CrudRepository<Song, Long> {

    static final String SONG_COLUMN_KEY = "id";
    static final String SONG_COLUMN_TITLE = "title";
    static final String SONG_COLUMN_AUTHOR_NAME = "author_name";
    static final String SONG_COLUMN_RAW_LYRICS = "raw_lyrics";
    static final String SONG_COLUMN_DESCRIPTION = "description";
    static final String SONG_COLUMN_COUPLETS = "couplets";
    static final String SONG_COLUMN_CHORUS = "chorus";
    static final String SONG_COLUMN_RECORDING = "recording";
    private static final String[] SONG_TABLE_COLUMNS = new String[]{
            SONG_COLUMN_KEY,
            SONG_COLUMN_TITLE,
            SONG_COLUMN_AUTHOR_NAME,
            SONG_COLUMN_RAW_LYRICS,
            SONG_COLUMN_COUPLETS,
            SONG_COLUMN_CHORUS,
            SONG_COLUMN_RECORDING
    };

    private static final int DATABASE_VERSION = 1;
    private static final String SONGS_TABLE_NAME = "songs";
    private static final String SONGS_TABLE_CREATE =
            "CREATE TABLE " + SONGS_TABLE_NAME + " ("
                    + SONG_COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + SONG_COLUMN_TITLE + " TEXT, "
                    + SONG_COLUMN_AUTHOR_NAME + " TEXT, "
                    + SONG_COLUMN_RAW_LYRICS + " TEXT, "
                    + SONG_COLUMN_CHORUS + " INTEGER, "
                    + SONG_COLUMN_COUPLETS + " INTEGER, "
                    + ");";
    public static final String DATABASE_NAME = "main_db";

    private SongRowMapper songRowMapper = new SongRowMapper();


    public SongsDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SONGS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public <S extends Song> S save(S songToSave) {
        Long entityId = songToSave.getId();
        ContentValues values = new ContentValues();
        values.put(SONG_COLUMN_TITLE, songToSave.getTitle());
        values.put(SONG_COLUMN_AUTHOR_NAME, songToSave.getAuthor());
        values.put(SONG_COLUMN_RAW_LYRICS, songToSave.getRawLyrics());
        if (entityId == null) {
            long insertedRowId = getWritableDatabase().insert(SONGS_TABLE_NAME, null, values);
            songToSave.setId(insertedRowId);
        } else {
            getWritableDatabase().update(SONGS_TABLE_NAME, values, SONG_COLUMN_KEY + " = ?", new String[]{Long.toString(entityId)});
        }
        return songToSave;
    }

    @Override
    public <S extends Song> List<S> save(Iterable<S> entities) {
        List<S> songsSaved = new ArrayList<>();
        for (S entity : entities) {
            songsSaved.add(save(entity));
        }
        return songsSaved;
    }

    @Override
    public Song findOne(Long key) {
        Cursor cursor = getReadableDatabase().query(SONGS_TABLE_NAME, SONG_TABLE_COLUMNS, SONG_COLUMN_KEY + " = ?", new String[]{Long.toString(key)}, null, null, null);
        Song song;
        if (cursor.moveToNext()) {
            song = songRowMapper.mapRow(cursor);
        } else {
            song = null;
        }
        cursor.close();
        return song;
    }

    private Song findOne(Integer key, SQLiteDatabase readableDatabase) {
        Cursor cursor = readableDatabase.query(SONGS_TABLE_NAME, SONG_TABLE_COLUMNS, SONG_COLUMN_KEY + " = ?", new String[]{Integer.toString(key)}, null, null, null);
        Song song;
        if (cursor.moveToNext()) {
            song = songRowMapper.mapRow(cursor);
        } else {
            song = null;
        }
        cursor.close();
        return song;
    }

    @Override
    public boolean exists(Long integer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Iterable<Song> findAll() {
        Cursor cursor = getReadableDatabase().query(SONGS_TABLE_NAME, SONG_TABLE_COLUMNS, null, null, null, null, null);
        List<Song> songs = new ArrayList<>();
        while (cursor.moveToNext()) {
            songs.add(songRowMapper.mapRow(cursor));
        }
        cursor.close();
        return songs;
    }

    @Override
    public Iterable<Song> findAll(Iterable<Long> integers) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public long count() {
        String columnName = "count";
        Cursor cursor = getReadableDatabase().query(SONGS_TABLE_NAME, new String[]{"Count(*) as " + columnName}, null, null, null, null, null);
        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndex(columnName));
        }
        cursor.close();
        return count;
    }

    @Override
    public void delete(Long key) {
        getWritableDatabase().delete(SONGS_TABLE_NAME, SONG_COLUMN_KEY + " = ?", new String[]{Long.toString(key)});
    }

    @Override
    public void delete(Song entity) {
        delete(entity.getId());
    }

    @Override
    public void delete(Iterable<? extends Song> entities) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void deleteAll() {
        getWritableDatabase().delete(SONGS_TABLE_NAME, null, null);
    }
}
