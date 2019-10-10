package com.frenchfrie.singasong.songs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.frenchfrie.data.CrudRepository;

import java.util.ArrayList;
import java.util.List;

import static com.frenchfrie.singasong.songs.CoupletRowMapper.COUPLET_COLUMN_IMAGE;
import static com.frenchfrie.singasong.songs.CoupletRowMapper.COUPLET_COLUMN_KEY;
import static com.frenchfrie.singasong.songs.CoupletRowMapper.COUPLET_COLUMN_VERSES;
import static com.frenchfrie.singasong.songs.SongContract.SongEntry.TABLE_NAME;
import static com.frenchfrie.singasong.songs.SongRowMapper.SONG_COLUMN_KEY;

public class CoupletsDAO implements CrudRepository<Couplet, Long> {

    public static final String COUPLET_COLUMN_SONG_KEY = "song_id";
    public static final String COUPLET_COLUMN_POSITION = "position";

    private static final String[] COUPLET_TABLE_COLUMNS = new String[]{
            COUPLET_COLUMN_KEY,
            COUPLET_COLUMN_SONG_KEY,
            COUPLET_COLUMN_POSITION,
            COUPLET_COLUMN_VERSES,
            COUPLET_COLUMN_IMAGE
    };

    private static final String COUPLETS_TABLE_NAME = "couplets";
    public static final String COUPLETS_TABLE_CREATE =
            "CREATE TABLE " + COUPLETS_TABLE_NAME + " ("
                    + COUPLET_COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + COUPLET_COLUMN_SONG_KEY + " INTEGER, "
                    + COUPLET_COLUMN_POSITION + " INTEGER, "
                    + COUPLET_COLUMN_VERSES + " TEXT, "
                    + COUPLET_COLUMN_IMAGE + " TEXT, "
                    + "FOREIGN KEY(" + COUPLET_COLUMN_SONG_KEY + ") REFERENCES " + TABLE_NAME + "(" + SONG_COLUMN_KEY + ")" +
                    ");";
    public static final int CHORUS_POSITION = 0;

    private SQLiteOpenHelper sqLiteOpenHelper;

    public CoupletsDAO(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    private CoupletRowMapper coupletRowMapper = new CoupletRowMapper();

    public <S extends Couplet> S save(S coupletToSave, long songId, Integer position) {
        Long entityId = coupletToSave.getId();
        ContentValues values = new ContentValues();
        coupletRowMapper.fillValues(coupletToSave, values);
        values.put(COUPLET_COLUMN_SONG_KEY, Long.toString(songId));
        values.put(COUPLET_COLUMN_POSITION, position == null ? Integer.toString(CHORUS_POSITION) : Integer.toString(position));
        if (entityId == null) {
            long insertedRowId = sqLiteOpenHelper.getWritableDatabase().insert(COUPLETS_TABLE_NAME, null, values);
            coupletToSave.setId(insertedRowId);
        } else {
            sqLiteOpenHelper.getWritableDatabase().update(COUPLETS_TABLE_NAME, values, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(entityId)});
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
        Cursor cursor = sqLiteOpenHelper.getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(key)}, null, null, null);
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
        Cursor cursor = sqLiteOpenHelper.getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS,
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
        Cursor cursor = sqLiteOpenHelper.getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS, null, null, null, null, null);
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
        Cursor cursor = sqLiteOpenHelper.getReadableDatabase().query(COUPLETS_TABLE_NAME, new String[]{"Count(*) as " + columnName}, null, null, null, null, null);
        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndex(columnName));
        }
        cursor.close();
        return count;
    }

    @Override
    public void delete(Long key) {
        sqLiteOpenHelper.getWritableDatabase().delete(COUPLETS_TABLE_NAME, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(key)});
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
        sqLiteOpenHelper.getWritableDatabase().delete(COUPLETS_TABLE_NAME, null, null);
    }

    public List<Couplet> findSongCouplets(Long songId) {
        Cursor cursor = sqLiteOpenHelper.getReadableDatabase().query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS,
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
