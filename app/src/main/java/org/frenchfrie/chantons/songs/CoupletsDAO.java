package org.frenchfrie.chantons.songs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.frenchfrie.sql_support.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class CoupletsDAO extends SQLiteOpenHelper implements CrudRepository<Couplet, Long> {

    static final String COUPLET_COLUMN_KEY = "id";
    static final String COUPLET_COLUMN_TITLE = "title";
    static final String COUPLET_COLUMN_LYRICS = "lyrics";
    static final String COUPLET_COLUMN_AUTHOR_NAME = "author_name";
    private static final String[] COUPLET_TABLE_COLUMNS = new String[]{COUPLET_COLUMN_KEY, COUPLET_COLUMN_TITLE, COUPLET_COLUMN_LYRICS, COUPLET_COLUMN_AUTHOR_NAME};

    private static final int DATABASE_VERSION = 1;
    private static final String COUPLETS_TABLE_NAME = "couplets";
    private static final String COUPLETS_TABLE_CREATE =
            "CREATE TABLE " + COUPLETS_TABLE_NAME + " ("
                    + COUPLET_COLUMN_KEY + " INTEGER PRIMARY KEY, "
                    + COUPLET_COLUMN_TITLE + " TEXT, "
                    + COUPLET_COLUMN_AUTHOR_NAME + " TEXT, "
                    + COUPLET_COLUMN_LYRICS + " TEXT);";
    public static final String DATABASE_NAME = "main_db";

    private CoupletRowMapper coupletRowMapper = new CoupletRowMapper();


    public CoupletsDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COUPLETS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public <S extends Couplet> S save(S coupletToSave) {
        Long entityId = coupletToSave.getId();
        ContentValues values = new ContentValues();
        values.put(COUPLET_COLUMN_TITLE, coupletToSave.getTitle());
        values.put(COUPLET_COLUMN_AUTHOR_NAME, coupletToSave.getAuthor());
        values.put(COUPLET_COLUMN_LYRICS, coupletToSave.getLyrics());
        if (entityId == null) {
            long insertedRowId = getWritableDatabase().insert(COUPLETS_TABLE_NAME, null, values);
            coupletToSave.setId((int) insertedRowId);
        } else {
            getWritableDatabase().update(COUPLETS_TABLE_NAME, values, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(entityId)});
        }
        return coupletToSave;
    }

    private Couplet save(Couplet coupletToSave, SQLiteDatabase db) {
        Long entityId = coupletToSave.getId();
        Couplet savedCouplet;
        ContentValues values = new ContentValues();
        values.put(COUPLET_COLUMN_TITLE, coupletToSave.getTitle());
        values.put(COUPLET_COLUMN_AUTHOR_NAME, coupletToSave.getAuthor());
        values.put(COUPLET_COLUMN_LYRICS, coupletToSave.getLyrics());
        if (entityId == null) {
            long insertedRowId = db.insert(COUPLETS_TABLE_NAME, null, values);
            savedCouplet = findOne((int) insertedRowId, db);
        } else {
            db.update(COUPLETS_TABLE_NAME, values, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(entityId)});
            savedCouplet = coupletToSave;
        }
        return savedCouplet;
    }

    @Override
    public <S extends Couplet> List<S> save(Iterable<S> entities) {
        List<S> coupletsSaved = new ArrayList<>();
        for (S entity : entities) {
            coupletsSaved.add(save(entity));
        }
        return coupletsSaved;
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

    private Couplet findOne(Long key, SQLiteDatabase readableDatabase) {
        Cursor cursor = readableDatabase.query(COUPLETS_TABLE_NAME, COUPLET_TABLE_COLUMNS, COUPLET_COLUMN_KEY + " = ?", new String[]{Long.toString(key)}, null, null, null);
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
}
