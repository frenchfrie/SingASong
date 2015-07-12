package org.frenchfrie.chantons.songs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.frenchfrie.sql_support.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class SongsDAO extends SQLiteOpenHelper implements CrudRepository<Song, Integer> {

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
    public static final String DATABASE_NAME = "main_db";

    private SongRowMapper songRowMapper = new SongRowMapper();


    public SongsDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static List<Song> dummies = new ArrayList<>();

    static {
        for (int i = 1; i <= 16; i++) {
            dummies.add(new Song("chanson " + i, "auteur " + i, "paroles " + i));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SONGS_TABLE_CREATE);
        for (Song dummy : dummies) {
            save(dummy, db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Song save(Song songToSave) {
        Integer entityId = songToSave.getId();
        Song savedSong;
        ContentValues values = new ContentValues();
        values.put(SONG_COLUMN_TITLE, songToSave.getTitle());
        values.put(SONG_COLUMN_AUTHOR_NAME, songToSave.getAuthor());
        values.put(SONG_COLUMN_LYRICS, songToSave.getLyrics());
        if (entityId == null) {
            long insertedRowId = getWritableDatabase().insert(SONGS_TABLE_NAME, null, values);
            savedSong = findOne((int) insertedRowId);
        } else {
            getWritableDatabase().update(SONGS_TABLE_NAME, values, SONG_COLUMN_KEY + " = ?", new String[]{Integer.toString(entityId)});
            savedSong = songToSave;
        }
        return savedSong;
    }

    private Song save(Song songToSave, SQLiteDatabase db) {
        Integer entityId = songToSave.getId();
        Song savedSong;
        ContentValues values = new ContentValues();
        values.put(SONG_COLUMN_TITLE, songToSave.getTitle());
        values.put(SONG_COLUMN_AUTHOR_NAME, songToSave.getAuthor());
        values.put(SONG_COLUMN_LYRICS, songToSave.getLyrics());
        if (entityId == null) {
            long insertedRowId = db.insert(SONGS_TABLE_NAME, null, values);
            savedSong = findOne((int) insertedRowId, db);
        } else {
            db.update(SONGS_TABLE_NAME, values, SONG_COLUMN_KEY + " = ?", new String[]{Integer.toString(entityId)});
            savedSong = songToSave;
        }
        return savedSong;
    }

    @Override
    public <S extends Song> List<S> save(Iterable<S> entities) {
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
    public boolean exists(Integer integer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Song> findAll() {
        Cursor cursor = getReadableDatabase().query(SONGS_TABLE_NAME, SONG_TABLE_COLUMNS, null, null, null, null, null);
        List<Song> songs = new ArrayList<>();
        while (cursor.moveToNext()) {
            songs.add(songRowMapper.mapRow(cursor));
        }
        cursor.close();
        return songs;
    }

    @Override
    public List<Song> findAll(Iterable<Integer> integers) {
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
