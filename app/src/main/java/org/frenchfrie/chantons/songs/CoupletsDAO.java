package org.frenchfrie.chantons.songs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.frenchfrie.sql_support.CrudRepository;

import java.util.ArrayList;
import java.util.List;

import static org.frenchfrie.chantons.songs.CoupletRowMapper.COUPLET_COLUMN_KEY;
import static org.frenchfrie.chantons.songs.CoupletRowMapper.COUPLET_COLUMN_VERSES;
import static org.frenchfrie.chantons.songs.SongsDAO.SONGS_TABLE_NAME;
import static org.frenchfrie.chantons.songs.SongRowMapper.SONG_COLUMN_KEY;

public class CoupletsDAO extends SQLiteOpenHelper implements CrudRepository<Couplet, Long> {

    public static final String COUPLET_COLUMN_SONG_KEY = "song_id";
    public static final String COUPLET_COLUMN_POSITION = "position";

    private static final String[] COUPLET_TABLE_COLUMNS = new String[]{COUPLET_COLUMN_KEY, COUPLET_COLUMN_SONG_KEY, COUPLET_COLUMN_POSITION, COUPLET_COLUMN_VERSES};

    private static final int DATABASE_VERSION = 1;
    private static final String COUPLETS_TABLE_NAME = "couplets";
    private static final String COUPLETS_TABLE_CREATE =
            "CREATE TABLE " + COUPLETS_TABLE_NAME + " ("
                    + COUPLET_COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + COUPLET_COLUMN_SONG_KEY + " INTEGER, "
                    + COUPLET_COLUMN_POSITION + " INTEGER, "
                    + COUPLET_COLUMN_VERSES + " TEXT, " +
                    "FOREIGN KEY(" + COUPLET_COLUMN_SONG_KEY + ") REFERENCES " + SONGS_TABLE_NAME + "(" + SONG_COLUMN_KEY + ")" +
                    ");";
    public static final String CHORUS_POSITION = "0";

    private CoupletRowMapper coupletRowMapper = new CoupletRowMapper();

    public CoupletsDAO(Context context) {
        super(context, SongsDAO.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COUPLETS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public <S extends Couplet> S save(S coupletToSave, long songId, Integer position) {
        Long entityId = coupletToSave.getId();
        ContentValues values = new ContentValues();
        coupletRowMapper.fillValues(coupletToSave, values);
        values.put(COUPLET_COLUMN_SONG_KEY, Long.toString(songId));
        values.put(COUPLET_COLUMN_POSITION, position == null ? CHORUS_POSITION : Integer.toString(position));
        if (entityId == null) {
            long insertedRowId = getWritableDatabase().insert(COUPLETS_TABLE_NAME, null, values);
            coupletToSave.setId(insertedRowId);
        } else {
            getWritableDatabase().update(COUPLETS_TABLE_NAME, values, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(entityId)});
        }
        return coupletToSave;
    }

    @Override
    public <S extends Couplet> S save(S entity) {
        throw new UnsupportedOperationException("Not to be implemented.");
    }

    @Override
    public <S extends Couplet> List<S> save(Iterable<S> entities) {
        throw new UnsupportedOperationException("Cannot save with several song ids and positions");
    }

    @Override
    public Couplet findOne(Long key) {
        Cursor cursor = getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(key)}, null, null, null);
        Couplet couplet;
        if (cursor.moveToNext()) {
            couplet = coupletRowMapper.mapRow(cursor);
        } else {
            couplet = null;
        }
        cursor.close();
        return couplet;
    }

    public Couplet findSongChorus(Long songId) {
        Cursor cursor = getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS,
                COUPLET_COLUMN_SONG_KEY + " = ? AND " + COUPLET_COLUMN_POSITION + " = " + CHORUS_POSITION,
                new String[]{Long.toString(songId)}, null, null, null);
        Couplet couplet;
        if (cursor.moveToNext()) {
            couplet = coupletRowMapper.mapRow(cursor);
        } else {
            couplet = null;
        }
        cursor.close();
        return couplet;
    }

    @Override
    public boolean exists(Long integer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<Couplet> findAll() {
        Cursor cursor = getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS, null, null, null, null, null);
        List<Couplet> couplets = new ArrayList<>();
        while (cursor.moveToNext()) {
            couplets.add(coupletRowMapper.mapRow(cursor));
        }
        cursor.close();
        return couplets;
    }

    @Override
    public List<Couplet> findAll(Iterable<Long> integers) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public long count() {
        String columnName = "count";
        Cursor cursor = getReadableDatabase().query(COUPLETS_TABLE_NAME, new String[]{"Count(*) as " + columnName}, null, null, null, null, null);
        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndex(columnName));
        }
        cursor.close();
        return count;
    }

    @Override
    public void delete(Long key) {
        getWritableDatabase().delete(COUPLETS_TABLE_NAME, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(key)});
    }

    @Override
    public void delete(Couplet entity) {
        delete(entity.getId());
    }

    @Override
    public void delete(Iterable<? extends Couplet> entities) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void deleteAll() {
        getWritableDatabase().delete(COUPLETS_TABLE_NAME, null, null);
    }

    public List<Couplet> findSongCouplets(Long songId) {
        Cursor cursor = getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS,
                COUPLET_COLUMN_SONG_KEY + " = ? AND " + COUPLET_COLUMN_POSITION + " <> " + CHORUS_POSITION,
                new String[]{Long.toString(songId)}, null, null, COUPLET_COLUMN_POSITION + " ASC");
        List<Couplet> couplets = new ArrayList<>();
        while (cursor.moveToNext()) {
            couplets.add(coupletRowMapper.mapRow(cursor));
        }
        cursor.close();
        return couplets;
    }
}
