package org.frenchfrie.data;

import android.database.Cursor;

public interface RowMapper<T> {
    T mapRow(Cursor cursor);
}
