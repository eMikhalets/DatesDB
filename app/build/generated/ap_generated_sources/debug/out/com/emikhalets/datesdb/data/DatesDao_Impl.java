package com.emikhalets.datesdb.data;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DatesDao_Impl implements DatesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DateItem> __insertionAdapterOfDateItem;

  private final EntityDeletionOrUpdateAdapter<DateItem> __deletionAdapterOfDateItem;

  private final EntityDeletionOrUpdateAdapter<DateItem> __updateAdapterOfDateItem;

  public DatesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDateItem = new EntityInsertionAdapter<DateItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `dates_table` (`id`,`name`,`date`,`type`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DateItem value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getDate());
        if (value.getType() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getType());
        }
      }
    };
    this.__deletionAdapterOfDateItem = new EntityDeletionOrUpdateAdapter<DateItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `dates_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DateItem value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfDateItem = new EntityDeletionOrUpdateAdapter<DateItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `dates_table` SET `id` = ?,`name` = ?,`date` = ?,`type` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DateItem value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getDate());
        if (value.getType() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getType());
        }
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void insert(final DateItem date) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDateItem.insert(date);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final DateItem date) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfDateItem.handle(date);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final DateItem date) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfDateItem.handle(date);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public DateItem getDate(final int id) {
    final String _sql = "SELECT * FROM dates_table WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final DateItem _result;
      if(_cursor.moveToFirst()) {
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _result = new DateItem(_tmpName,_tmpDate,_tmpType);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<DateItem> getAllDates() {
    final String _sql = "SELECT * FROM dates_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final List<DateItem> _result = new ArrayList<DateItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DateItem _item;
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final long _tmpDate;
        _tmpDate = _cursor.getLong(_cursorIndexOfDate);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _item = new DateItem(_tmpName,_tmpDate,_tmpType);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
