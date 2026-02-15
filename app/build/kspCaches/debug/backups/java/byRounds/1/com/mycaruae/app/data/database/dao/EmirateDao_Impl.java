package com.mycaruae.app.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mycaruae.app.data.database.entity.EmirateEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EmirateDao_Impl implements EmirateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EmirateEntity> __insertionAdapterOfEmirateEntity;

  public EmirateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmirateEntity = new EntityInsertionAdapter<EmirateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `emirates` (`id`,`nameEn`,`trafficAuthority`,`registrationUrl`,`inspectionUrl`,`phone`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmirateEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNameEn());
        statement.bindString(3, entity.getTrafficAuthority());
        statement.bindString(4, entity.getRegistrationUrl());
        if (entity.getInspectionUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getInspectionUrl());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhone());
        }
      }
    };
  }

  @Override
  public Object insertAll(final List<EmirateEntity> emirates,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEmirateEntity.insert(emirates);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EmirateEntity>> getAll() {
    final String _sql = "SELECT * FROM emirates ORDER BY nameEn ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emirates"}, new Callable<List<EmirateEntity>>() {
      @Override
      @NonNull
      public List<EmirateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfTrafficAuthority = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficAuthority");
          final int _cursorIndexOfRegistrationUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "registrationUrl");
          final int _cursorIndexOfInspectionUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "inspectionUrl");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final List<EmirateEntity> _result = new ArrayList<EmirateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmirateEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameEn;
            _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            final String _tmpTrafficAuthority;
            _tmpTrafficAuthority = _cursor.getString(_cursorIndexOfTrafficAuthority);
            final String _tmpRegistrationUrl;
            _tmpRegistrationUrl = _cursor.getString(_cursorIndexOfRegistrationUrl);
            final String _tmpInspectionUrl;
            if (_cursor.isNull(_cursorIndexOfInspectionUrl)) {
              _tmpInspectionUrl = null;
            } else {
              _tmpInspectionUrl = _cursor.getString(_cursorIndexOfInspectionUrl);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            _item = new EmirateEntity(_tmpId,_tmpNameEn,_tmpTrafficAuthority,_tmpRegistrationUrl,_tmpInspectionUrl,_tmpPhone);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<EmirateEntity> getById(final String id) {
    final String _sql = "SELECT * FROM emirates WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emirates"}, new Callable<EmirateEntity>() {
      @Override
      @Nullable
      public EmirateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
          final int _cursorIndexOfTrafficAuthority = CursorUtil.getColumnIndexOrThrow(_cursor, "trafficAuthority");
          final int _cursorIndexOfRegistrationUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "registrationUrl");
          final int _cursorIndexOfInspectionUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "inspectionUrl");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final EmirateEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNameEn;
            _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
            final String _tmpTrafficAuthority;
            _tmpTrafficAuthority = _cursor.getString(_cursorIndexOfTrafficAuthority);
            final String _tmpRegistrationUrl;
            _tmpRegistrationUrl = _cursor.getString(_cursorIndexOfRegistrationUrl);
            final String _tmpInspectionUrl;
            if (_cursor.isNull(_cursorIndexOfInspectionUrl)) {
              _tmpInspectionUrl = null;
            } else {
              _tmpInspectionUrl = _cursor.getString(_cursorIndexOfInspectionUrl);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            _result = new EmirateEntity(_tmpId,_tmpNameEn,_tmpTrafficAuthority,_tmpRegistrationUrl,_tmpInspectionUrl,_tmpPhone);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
