package bj4.dev.yhh.lotteryupdater

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.remote.LotteryRepository
import bj4.dev.yhh.lotteryparser.dao.Lottery
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

abstract class UpdateTask(context: Context, param: WorkerParameters) :
    CoroutineWorker(context, param),
    KoinComponent {

    private val repository: LotteryRepository by inject()

    override suspend fun doWork(): Result {
        var rtn: Result? = null

        setForeground(createForegroundInfo())

        repository.update(getLotteryType())
            .blockingSubscribe(
                { doOnNext(it) },
                {
                    doOnError(it)
                    Timber.w(it)
                    rtn = if (it is LotteryRepository.LoadFinishedException) {
                        Result.success()
                    } else {
                        Result.retry()
                    }
                }, {
                    doOnComplete()
                    rtn = Result.success()
                }
            )
        return rtn ?: Result.success()
    }

    abstract fun getNotificationId(): Int

    abstract fun getLotteryType(): LotteryType

    open fun doOnNext(list: List<Lottery>) {}

    open fun doOnComplete() {}

    open fun doOnError(throwable: Throwable) {}

    abstract fun getNotificationTitle(): String

    private fun createForegroundInfo(): ForegroundInfo {
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val title = getNotificationTitle()
        val notification = NotificationCompat.Builder(applicationContext, getChannelID())
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(applicationContext.getString(R.string.notification_updater_content))
            .setSmallIcon(R.drawable.ic_cloud_download_outline_white_24dp)
            .setOngoing(true)
            .addAction(
                android.R.drawable.ic_delete,
                applicationContext.getString(android.R.string.cancel),
                intent
            )
            .build()

        return ForegroundInfo(getNotificationId(), notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = applicationContext.getString(R.string.notification_updater_channel_name)
        val descriptionText =
            applicationContext.getString(R.string.notification_updater_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(getChannelID(), name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun getChannelID() = applicationContext.getString(R.string.notification_channel_id)
}