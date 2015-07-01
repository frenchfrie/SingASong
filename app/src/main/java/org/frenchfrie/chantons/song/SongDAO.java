package org.frenchfrie.chantons.song;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.frenchfrie.sql_support.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class SongDAO extends SQLiteOpenHelper implements CrudRepository<Song, Integer> {

    static final String SONG_COLUMN_KEY = "id";
    static final String SONG_COLUMN_TITLE = "title";
    static final String SONG_COLUMN_LYRICS = "lyrics";
    static final String SONG_COLUMN_AUTHOR_NAME = "author_name";
    private static final String[] SONG_TABLE_COLUMNS = new String[]{SONG_COLUMN_KEY, SONG_COLUMN_TITLE, SONG_COLUMN_LYRICS, SONG_COLUMN_AUTHOR_NAME};

    private static final int DATABASE_VERSION = 1;
    private static final String SONGS_TABLE_NAME = "songs";
    private static final String SONGS_TABLE_CREATE =
            "CREATE TABLE " + SONGS_TABLE_NAME + " ("
                    + SONG_COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + SONG_COLUMN_TITLE + " TEXT, "
                    + SONG_COLUMN_AUTHOR_NAME + " TEXT, "
                    + SONG_COLUMN_LYRICS + " TEXT);";

    private SongRowMapper songRowMapper = new SongRowMapper();


    public SongDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SongDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SONGS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public <S extends Song> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Song> Iterable<S> save(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Song findOne(Integer key) {
        Cursor cursor = getReadableDatabase().query(SONGS_TABLE_NAME, SONG_TABLE_COLUMNS, SONG_COLUMN_KEY + " = ?", new String[]{Integer.toString(key)}, null, null, null);
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
    public boolean exists(Integer integer) {
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
    public Iterable<Song> findAll(Iterable<Integer> integers) {
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
    public void delete(Integer key) {
        getWritableDatabase().delete(SONGS_TABLE_NAME, SONG_COLUMN_KEY + " = ?", new String[]{Integer.toString(key)});
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
