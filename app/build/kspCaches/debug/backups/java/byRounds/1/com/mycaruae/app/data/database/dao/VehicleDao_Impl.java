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
import com.mycaruae.app.data.database.entity.VehicleEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VehicleDao_Impl implements VehicleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VehicleEntity> __insertionAdapterOfVehicleEntity;

  private final EntityDeletionOrUpdateAdapter<VehicleEntity> __updateAdapterOfVehicleEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMileage;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRegistrationExpiry;

  private final SharedSQLiteStatement __preparedStmtOfUpdateInspectionExpiry;

  public VehicleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVehicleEntity = new EntityInsertionAdapter<VehicleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vehicles` (`id`,`userId`,`brandId`,`modelId`,`year`,`emirate`,`registrationExpiry`,`inspectionExpiry`,`vin`,`plateNumber`,`color`,`currentMileage`,`photoUris`,`pendingSync`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VehicleEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getBrandId());
        statement.bindString(4, entity.getModelId());
        statement.bindLong(5, entity.getYear());
        statement.bindString(6, entity.getEmirate());
        statement.bindLong(7, entity.getRegistrationExpiry());
        statement.bindLong(8, entity.getInspectionExpiry());
        if (entity.getVin() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getVin());
        }
        if (entity.getPlateNumber() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPlateNumber());
        }
        if (entity.getColor() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getColor());
        }
        statement.bindLong(12, entity.getCurrentMileage());
        if (entity.getPhotoUris() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getPhotoUris());
        }
        final int _tmp = entity.getPendingSync() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfVehicleEntity = new EntityDeletionOrUpdateAdapter<VehicleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `vehicles` SET `id` = ?,`userId` = ?,`brandId` = ?,`modelId` = ?,`year` = ?,`emirate` = ?,`registrationExpiry` = ?,`inspectionExpiry` = ?,`vin` = ?,`plateNumber` = ?,`color` = ?,`currentMileage` = ?,`photoUris` = ?,`pendingSync` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VehicleEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getBrandId());
        statement.bindString(4, entity.getModelId());
        statement.bindLong(5, entity.getYear());
        statement.bindString(6, entity.getEmirate());
        statement.bindLong(7, entity.getRegistrationExpiry());
        statement.bindLong(8, entity.getInspectionExpiry());
        if (entity.getVin() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getVin());
        }
        if (entity.getPlateNumber() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPlateNumber());
        }
        if (entity.getColor() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getColor());
        }
        statement.bindLong(12, entity.getCurrentMileage());
        if (entity.getPhotoUris() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getPhotoUris());
        }
        final int _tmp = entity.getPendingSync() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getUpdatedAt());
        statement.bindString(17, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vehicles WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateMileage = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE vehicles SET currentMileage = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateRegistrationExpiry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE vehicles SET registrationExpiry = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateInspectionExpiry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE vehicles SET inspectionExpiry = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final VehicleEntity vehicle, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVehicleEntity.insert(vehicle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final VehicleEntity vehicle, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVehicleEntity.handle(vehicle);
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
  public Object updateMileage(final String vehicleId, final int mileage, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMileage.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, mileage);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, vehicleId);
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
          __preparedStmtOfUpdateMileage.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRegistrationExpiry(final String vehicleId, final long expiry,
      final long updatedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRegistrationExpiry.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, expiry);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, vehicleId);
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
          __preparedStmtOfUpdateRegistrationExpiry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateInspectionExpiry(final String vehicleId, final long expiry,
      final long updatedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateInspectionExpiry.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, expiry);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, vehicleId);
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
          __preparedStmtOfUpdateInspectionExpiry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<VehicleEntity> getByUserId(final String userId) {
    final String _sql = "SELECT * FROM vehicles WHERE userId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vehicles"}, new Callable<VehicleEntity>() {
      @Override
      @Nullable
      public VehicleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfBrandId = CursorUtil.getColumnIndexOrThrow(_cursor, "brandId");
          final int _cursorIndexOfModelId = CursorUtil.getColumnIndexOrThrow(_cursor, "modelId");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfEmirate = CursorUtil.getColumnIndexOrThrow(_cursor, "emirate");
          final int _cursorIndexOfRegistrationExpiry = CursorUtil.getColumnIndexOrThrow(_cursor, "registrationExpiry");
          final int _cursorIndexOfInspectionExpiry = CursorUtil.getColumnIndexOrThrow(_cursor, "inspectionExpiry");
          final int _cursorIndexOfVin = CursorUtil.getColumnIndexOrThrow(_cursor, "vin");
          final int _cursorIndexOfPlateNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "plateNumber");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCurrentMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "currentMileage");
          final int _cursorIndexOfPhotoUris = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUris");
          final int _cursorIndexOfPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingSync");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final VehicleEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpBrandId;
            _tmpBrandId = _cursor.getString(_cursorIndexOfBrandId);
            final String _tmpModelId;
            _tmpModelId = _cursor.getString(_cursorIndexOfModelId);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final String _tmpEmirate;
            _tmpEmirate = _cursor.getString(_cursorIndexOfEmirate);
            final long _tmpRegistrationExpiry;
            _tmpRegistrationExpiry = _cursor.getLong(_cursorIndexOfRegistrationExpiry);
            final long _tmpInspectionExpiry;
            _tmpInspectionExpiry = _cursor.getLong(_cursorIndexOfInspectionExpiry);
            final String _tmpVin;
            if (_cursor.isNull(_cursorIndexOfVin)) {
              _tmpVin = null;
            } else {
              _tmpVin = _cursor.getString(_cursorIndexOfVin);
            }
            final String _tmpPlateNumber;
            if (_cursor.isNull(_cursorIndexOfPlateNumber)) {
              _tmpPlateNumber = null;
            } else {
              _tmpPlateNumber = _cursor.getString(_cursorIndexOfPlateNumber);
            }
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final int _tmpCurrentMileage;
            _tmpCurrentMileage = _cursor.getInt(_cursorIndexOfCurrentMileage);
            final String _tmpPhotoUris;
            if (_cursor.isNull(_cursorIndexOfPhotoUris)) {
              _tmpPhotoUris = null;
            } else {
              _tmpPhotoUris = _cursor.getString(_cursorIndexOfPhotoUris);
            }
            final boolean _tmpPendingSync;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPendingSync);
            _tmpPendingSync = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new VehicleEntity(_tmpId,_tmpUserId,_tmpBrandId,_tmpModelId,_tmpYear,_tmpEmirate,_tmpRegistrationExpiry,_tmpInspectionExpiry,_tmpVin,_tmpPlateNumber,_tmpColor,_tmpCurrentMileage,_tmpPhotoUris,_tmpPendingSync,_tmpCreatedAt,_tmpUpdatedAt);
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
