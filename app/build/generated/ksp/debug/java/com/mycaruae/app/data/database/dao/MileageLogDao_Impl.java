package com.mycaruae.app.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mycaruae.app.data.database.entity.MileageLogEntity;
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
public final class MileageLogDao_Impl implements MileageLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MileageLogEntity> __insertionAdapterOfMileageLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public MileageLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMileageLogEntity = new EntityInsertionAdapter<MileageLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `mileage_logs` (`id`,`vehicleId`,`mileage`,`recordedDate`,`pendingSync`,`createdAt`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MileageLogEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getVehicleId());
        statement.bindLong(3, entity.getMileage());
        statement.bindLong(4, entity.getRecordedDate());
        final int _tmp = entity.getPendingSync() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM mileage_logs WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MileageLogEntity log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMileageLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MileageLogEntity>> getByVehicle(final String vehicleId) {
    final String _sql = "SELECT * FROM mileage_logs WHERE vehicleId = ? ORDER BY recordedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, vehicleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"mileage_logs"}, new Callable<List<MileageLogEntity>>() {
      @Override
      @NonNull
      public List<MileageLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
          final int _cursorIndexOfRecordedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedDate");
          final int _cursorIndexOfPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingSync");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<MileageLogEntity> _result = new ArrayList<MileageLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MileageLogEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVehicleId;
            _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            final int _tmpMileage;
            _tmpMileage = _cursor.getInt(_cursorIndexOfMileage);
            final long _tmpRecordedDate;
            _tmpRecordedDate = _cursor.getLong(_cursorIndexOfRecordedDate);
            final boolean _tmpPendingSync;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPendingSync);
            _tmpPendingSync = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new MileageLogEntity(_tmpId,_tmpVehicleId,_tmpMileage,_tmpRecordedDate,_tmpPendingSync,_tmpCreatedAt);
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
  public Object getLatest(final String vehicleId,
      final Continuation<? super MileageLogEntity> $completion) {
    final String _sql = "SELECT * FROM mileage_logs WHERE vehicleId = ? ORDER BY mileage DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, vehicleId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MileageLogEntity>() {
      @Override
      @Nullable
      public MileageLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
          final int _cursorIndexOfRecordedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedDate");
          final int _cursorIndexOfPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingSync");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final MileageLogEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVehicleId;
            _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            final int _tmpMileage;
            _tmpMileage = _cursor.getInt(_cursorIndexOfMileage);
            final long _tmpRecordedDate;
            _tmpRecordedDate = _cursor.getLong(_cursorIndexOfRecordedDate);
            final boolean _tmpPendingSync;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPendingSync);
            _tmpPendingSync = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new MileageLogEntity(_tmpId,_tmpVehicleId,_tmpMileage,_tmpRecordedDate,_tmpPendingSync,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
