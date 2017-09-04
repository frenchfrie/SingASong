package org.frenchfrie.chantons.songs;

import android.content.ContentValues;
import android.database.Cursor;

import org.frenchfrie.data.RowMapper;

public class CoupletRowMapper implements RowMapper<Couplet> {

    public static final String COUPLET_COLUMN_KEY = "id";
    public static final String COUPLET_COLUMN_VERSES = "verses";
    public static final String COUPLET_COLUMN_IMAGE = "image";

    @Override
    public Couplet mapRow(Cursor cursor) {
        Couplet couplet = new Couplet();
        couplet.setId(cursor.getLong(cursor.getColumnIndex(COUPLET_COLUMN_KEY)));
        couplet.setVerses(cursor.getString(cursor.getColumnIndex(COUPLET_COLUMN_VERSES)));
        couplet.setImage(cursor.getString(cursor.getColumnIndex(COUPLET_COLUMN_IMAGE)));
        return couplet;
    }

    public ContentValues fillValues(Couplet couplet, ContentValues contentValues) {
        if (contentValues == null) contentValues = new ContentValues();
        contentValues.put(COUPLET_COLUMN_KEY, couplet.getId());
        contentValues.put(COUPLET_COLUMN_IMAGE, couplet.getImage());
        contentValues.put(COUPLET_COLUMN_VERSES, couplet.getVerses());
        return contentValues;
    }
}
