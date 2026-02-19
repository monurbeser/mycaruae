package com.mycaruae.app.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mycaruae.app.data.datastore.UserPreferences
import com.mycaruae.app.data.repository.ReminderRepository
import com.mycaruae.app.data.repository.VehicleRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val vehicleRepository: VehicleRepository,
    private val reminderRepository: ReminderRepository,
    private val userPreferences: UserPreferences,
    private val notificationHelper: NotificationHelper,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val userData = userPreferences.userData.first()
            if (!userData.isLoggedIn) return Result.success()

            val vehicles = vehicleRepository.getAllVehicles(userData.userId).first()
            if (vehicles.isEmpty()) return Result.success()

            val now = System.currentTimeMillis()

            vehicles.forEach { vehicle ->
                val reminders = reminderRepository.getAll(vehicle.id).first()

                val plate = vehicle.plateNumber ?: ""
                val vehicleLabel = "${vehicle.modelId} ${vehicle.year}" +
                        if (plate.isNotBlank()) " • $plate" else ""

                reminders
                    .filter { it.status == "PENDING" && it.dueDate != null && it.dueDate <= now }
                    .forEach { reminder ->
                        val notificationId = reminder.id.hashCode()

                        when (reminder.type) {
                            "REGISTRATION" -> {
                                notificationHelper.showRegistrationReminder(
                                    title = "Registration Reminder",
                                    body = reminder.title,
                                    notificationId = notificationId,
                                    vehicleLabel = vehicleLabel,
                                )
                            }
                            "INSPECTION" -> {
                                notificationHelper.showInspectionReminder(
                                    title = "Inspection Reminder",
                                    body = reminder.title,
                                    notificationId = notificationId,
                                    vehicleLabel = vehicleLabel,
                                )
                            }
                            else -> {
                                notificationHelper.showMaintenanceReminder(
                                    title = "Maintenance Reminder",
                                    body = reminder.title,
                                    notificationId = notificationId,
                                    vehicleLabel = vehicleLabel,
                                )
                            }
                        }

                        reminderRepository.completeReminder(reminder)
                    }
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val WORK_NAME = "reminder_check"

        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<ReminderWorker>(
                6, TimeUnit.HOURS,
                30, TimeUnit.MINUTES,
            ).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request,
            )
        }

        fun runNow(context: Context) {
            val request = OneTimeWorkRequestBuilder<ReminderWorker>().build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                "reminder_check_now",
                ExistingWorkPolicy.REPLACE,
                request,
            )
        }
    }
}