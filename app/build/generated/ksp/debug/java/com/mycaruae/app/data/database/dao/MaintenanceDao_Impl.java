package com.mycaruae.app.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mycaruae.app.data.database.entity.MaintenanceEntity;
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
public final class MaintenanceDao_Impl implements MaintenanceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MaintenanceEntity> __insertionAdapterOfMaintenanceEntity;

  private final EntityDeletionOrUpdateAdapter<MaintenanceEntity> __updateAdapterOfMaintenanceEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public MaintenanceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMaintenanceEntity = new EntityInsertionAdapter<MaintenanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `maintenance_records` (`id`,`vehicleId`,`type`,`description`,`serviceDate`,`mileageAtService`,`cost`,`serviceProvider`,`pendingSync`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MaintenanceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getVehicleId());
        statement.bindString(3, entity.getType());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindLong(5, entity.getServiceDate());
        statement.bindLong(6, entity.getMileageAtService());
        statement.bindDouble(7, entity.getCost());
        if (entity.getServiceProvider() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getServiceProvider());
        }
        final int _tmp = entity.getPendingSync() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfMaintenanceEntity = new EntityDeletionOrUpdateAdapter<MaintenanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `maintenance_records` SET `id` = ?,`vehicleId` = ?,`type` = ?,`description` = ?,`serviceDate` = ?,`mileageAtService` = ?,`cost` = ?,`serviceProvider` = ?,`pendingSync` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MaintenanceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getVehicleId());
        statement.bindString(3, entity.getType());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindLong(5, entity.getServiceDate());
        statement.bindLong(6, entity.getMileageAtService());
        statement.bindDouble(7, entity.getCost());
        if (entity.getServiceProvider() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getServiceProvider());
        }
        final int _tmp = entity.getPendingSync() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindString(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM maintenance_records WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MaintenanceEntity record,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMaintenanceEntity.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final MaintenanceEntity record,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMaintenanceEntity.handle(record);
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
  public Flow<List<MaintenanceEntity>> getByVehicle(final String vehicleId) {
    final String _sql = "SELECT * FROM maintenance_records WHERE vehicleId = ? ORDER BY serviceDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, vehicleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"maintenance_records"}, new Callable<List<MaintenanceEntity>>() {
      @Override
      @NonNull
      public List<MaintenanceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfServiceDate = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceDate");
          final int _cursorIndexOfMileageAtService = CursorUtil.getColumnIndexOrThrow(_cursor, "mileageAtService");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfServiceProvider = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceProvider");
          final int _cursorIndexOfPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingSync");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<MaintenanceEntity> _result = new ArrayList<MaintenanceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MaintenanceEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVehicleId;
            _tmpVehicleId = _cursor.getString(_cursorIndexOfVehicleId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final long _tmpServiceDate;
            _tmpServiceDate = _cursor.getLong(_cursorIndexOfServiceDate);
            final int _tmpMileageAtService;
            _tmpMileageAtService = _cursor.getInt(_cursorIndexOfMileageAtService);
            final double _tmpCost;
            _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            final String _tmpServiceProvider;
            if (_cursor.isNull(_cursorIndexOfServiceProvider)) {
              _tmpServiceProvider = null;
            } else {
              _tmpServiceProvider = _cursor.getString(_cursorIndexOfServiceProvider);
            }
            final boolean _tmpPendingSync;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPendingSync);
            _tmpPendingSync = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MaintenanceEntity(_tmpId,_tmpVehicleId,_tmpType,_tmpDescription,_tmpServiceDate,_tmpMileageAtService,_tmpCost,_tmpServiceProvider,_tmpPendingSync,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Double> getTotalCost(final String vehicleId) {
    final String _sql = "SELECT SUM(cost) FROM maintenance_records WHERE vehicleId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, vehicleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"maintenance_records"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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
