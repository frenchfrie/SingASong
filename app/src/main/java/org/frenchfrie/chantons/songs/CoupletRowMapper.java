package org.frenchfrie.chantons.songs;

import android.database.Cursor;

import org.frenchfrie.sql_support.RowMapper;

public class CoupletRowMapper implements RowMapper<Couplet> {

    @Override
    public Couplet mapRow(Cursor cursor) {
        Couplet couplet = new Couplet();
        couplet.setId(cursor.getLong(cursor.getColumnIndex(CoupletsDAO.COUPLET_COLUMN_KEY)));
        return couplet;
    }
}
