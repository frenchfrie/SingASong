package org.frenchfrie.sql_support;

import android.database.Cursor;

public interface RowMapper<T> {
    T mapRow(Cursor cursor);
}
