package com.mycaruae.app.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mycaruae.app.data.database.entity.RenewalLogEntity;
import java.lang.Class;
import java.lang.Double;
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
public final class RenewalLogDao_Impl implements RenewalLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RenewalLogEntity> __insertionAdapterOfRenewalLogEntity;

  public RenewalLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRenewalLogEntity = new EntityInsertionAdapter<RenewalLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `renewal_logs` (`id`,`vehicleId`,`renewalType`,`previousExpiry`,`newExpiry`,`renewalDate`,`cost`,`note`,`pendingSync`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RenewalLogEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getVehicleId());
        statement.bindString(3, entity.getRenewalType());
        statement.bindLong(4, entity.getPreviousExpiry());
        statement.bindLong(5, entity.getNewExpiry());
        statement.bindLong(6, entity.getRenewalDate());
        if (entity.getCost() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getCost());
        }
        if (entity.getNote() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNote());
        }
        final int _tmp = entity.getPendingSync() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
  }

  @Override
  public Object insert(final RenewalLogEntity log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRenewalLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RenewalLogEntity>> getByVehicle(final String vehicleId) {
    final String _sql = "SELECT * FROM renewal_logs WHERE vehicleId = ? ORDER BY renewalDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, vehicleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"renewal_logs"}, new Callable<List<RenewalLogEntity>>() {
      @Override
      @NonNull
      public List<RenewalLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfRenewalType = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalType");
          final int _cursorIndexOfPreviousExpiry = CursorUtil.getColumnIndexOrThrow(_cursor, "previousExpiry");
          final int _cursorIndexOfNewExpiry = CursorUtil.getColumnIndexOrThrow(_cursor, "newExpiry");
          final int _cursorIndexOfRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "renewalDate");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingSync");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<RenewalLogEntity> _result = new ArrayList<RenewalLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RenewalLogEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVehicleId;
            _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            final String _tmpRenewalType;
            _tmpRenewalType = _cursor.getString(_cursorIndexOfRenewalType);
            final long _tmpPreviousExpiry;
            _tmpPreviousExpiry = _cursor.getLong(_cursorIndexOfPreviousExpiry);
            final long _tmpNewExpiry;
            _tmpNewExpiry = _cursor.getLong(_cursorIndexOfNewExpiry);
            final long _tmpRenewalDate;
            _tmpRenewalDate = _cursor.getLong(_cursorIndexOfRenewalDate);
            final Double _tmpCost;
            if (_cursor.isNull(_cursorIndexOfCost)) {
              _tmpCost = null;
            } else {
              _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final boolean _tmpPendingSync;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPendingSync);
            _tmpPendingSync = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new RenewalLogEntity(_tmpId,_tmpVehicleId,_tmpRenewalType,_tmpPreviousExpiry,_tmpNewExpiry,_tmpRenewalDate,_tmpCost,_tmpNote,_tmpPendingSync,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
