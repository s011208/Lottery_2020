package bj4.dev.yhh.lotteryupdater

import android.content.Context
import androidx.work.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class WorkerInit(private val context: Context) {
    companion object {
        private val UPDATE_HOUR_INTERVAL = if (BuildConfig.DEBUG) 1L else 8L
        private val UPDATE_TIME_UNIT = TimeUnit.HOURS

        private const val BACKOFF_DELAY = 15L
        private val BACKOFF_POLICY = BackoffPolicy.LINEAR
        private val BACKOFF_TIME_UNIT = TimeUnit.MINUTES
    }

    fun start() {
        val ltoUpdateRequest =
            PeriodicWorkRequestBuilder<UpdateLtoTask>(UPDATE_HOUR_INTERVAL, UPDATE_TIME_UNIT)
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .setBackoffCriteria(BACKOFF_POLICY, BACKOFF_DELAY, BACKOFF_TIME_UNIT)
                .addTag(UpdateLtoTask::class.java.simpleName)
                .build()

        val ltoBigUpdateRequest =
            PeriodicWorkRequestBuilder<UpdateLtoBigTask>(UPDATE_HOUR_INTERVAL, UPDATE_TIME_UNIT)
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .setBackoffCriteria(BACKOFF_POLICY, BACKOFF_DELAY, BACKOFF_TIME_UNIT)
                .addTag(UpdateLtoTask::class.java.simpleName)
                .build()

        val ltoHKUpdateRequest =
            PeriodicWorkRequestBuilder<UpdateLtoHKTask>(UPDATE_HOUR_INTERVAL, UPDATE_TIME_UNIT)
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .setBackoffCriteria(BACKOFF_POLICY, BACKOFF_DELAY, BACKOFF_TIME_UNIT)
                .addTag(UpdateLtoTask::class.java.simpleName)
                .build()

        with(WorkManager.getInstance(context.applicationContext)) {
            enqueueUniquePeriodicWork(
                UpdateLtoTask::class.java.simpleName,
                ExistingPeriodicWorkPolicy.KEEP,
                ltoUpdateRequest
            )

            enqueueUniquePeriodicWork(
                UpdateLtoBigTask::class.java.simpleName,
                ExistingPeriodicWorkPolicy.KEEP,
                ltoBigUpdateRequest
            )

            enqueueUniquePeriodicWork(
                UpdateLtoHKTask::class.java.simpleName,
                ExistingPeriodicWorkPolicy.KEEP,
                ltoHKUpdateRequest
            )
        }
    }
}