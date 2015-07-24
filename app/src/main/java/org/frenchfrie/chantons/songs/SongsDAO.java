package org.frenchfrie.chantons.songs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.frenchfrie.sql_support.CrudRepository;

import java.util.ArrayList;
import java.util.List;

import static org.frenchfrie.chantons.songs.SongRowMapper.SONG_COLUMN_AUTHOR_NAME;
import static org.frenchfrie.chantons.songs.SongRowMapper.SONG_COLUMN_DESCRIPTION;
import static org.frenchfrie.chantons.songs.SongRowMapper.SONG_COLUMN_KEY;
import static org.frenchfrie.chantons.songs.SongRowMapper.SONG_COLUMN_RAW_LYRICS;
import static org.frenchfrie.chantons.songs.SongRowMapper.SONG_COLUMN_RECORDING;
import static org.frenchfrie.chantons.songs.SongRowMapper.SONG_COLUMN_TITLE;

public class SongsDAO extends SQLiteOpenHelper implements CrudRepository<Song, Long> {

    public static final int DATABASE_VERSION = 1;

    private static final String[] SONG_TABLE_COLUMNS = new String[]{
            SONG_COLUMN_KEY,
            SONG_COLUMN_TITLE,
            SONG_COLUMN_DESCRIPTION,
            SONG_COLUMN_AUTHOR_NAME,
            SONG_COLUMN_RAW_LYRICS,
            SONG_COLUMN_RECORDING
    };

    public static final String SONGS_TABLE_NAME = "songs";
    private static final String SONGS_TABLE_CREATE =
            "CREATE TABLE " + SONGS_TABLE_NAME + " ("
                    + SONG_COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + SONG_COLUMN_TITLE + " TEXT, "
                    + SONG_COLUMN_DESCRIPTION + " TEXT, "
                    + SONG_COLUMN_AUTHOR_NAME + " TEXT, "
                    + SONG_COLUMN_RAW_LYRICS + " TEXT, "
                    + SONG_COLUMN_RECORDING + " TEXT"
                    + ");";
    public static final String DATABASE_NAME = "main_db";

    public CoupletsDAO getCoupletsDAO() {
        return coupletsDAO;
    }

    private final CoupletsDAO coupletsDAO;

    private SongRowMapper songRowMapper;

    public SongsDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        coupletsDAO = new CoupletsDAO(this);
        songRowMapper = new SongRowMapper(coupletsDAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SONGS_TABLE_CREATE);
        db.execSQL(CoupletsDAO.COUPLETS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public <S extends Song> S save(S songToSave) {
        Long entityId = songToSave.getId();
        ContentValues values = new ContentValues();
        values = songRowMapper.fillValues(songToSave, values);
        if (entityId == null) {
            long insertedRowId = getWritableDatabase().insert(SONGS_TABLE_NAME, null, values);
            songToSave.setId(insertedRowId);
        } else {
            getWritableDatabase().update(SONGS_TABLE_NAME, values, SONG_COLUMN_KEY + " = ?", new String[]{Long.toString(entityId)});
        }
        Couplet chorus = songToSave.getChorus();
        if (chorus != null) {
            coupletsDAO.save(chorus, songToSave.getId(), CoupletsDAO.CHORUS_POSITION);
        }
        int coupletPosition = 1;
        for (Couplet couplet : songToSave.getCouplets()) {
            coupletsDAO.save(couplet, songToSave.getId(), coupletPosition++);
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

    @Override
    public boolean exists(Long integer) {
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
