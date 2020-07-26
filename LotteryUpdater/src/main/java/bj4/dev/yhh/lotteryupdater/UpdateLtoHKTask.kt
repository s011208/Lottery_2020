package bj4.dev.yhh.lotteryupdater

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.remote.LotteryRepository
import bj4.dev.yhh.lotteryparser.dao.Lottery
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class UpdateLtoHKTask(context: Context, param: WorkerParameters): UpdateTask(context, param) {
    override fun getNotificationId(): Int {
        return 900002
    }

    override fun getLotteryType(): LotteryType {
        return LotteryType.LtoHK
    }

    override fun getNotificationTitle(): String {
        return applicationContext.getString(R.string.notification_lto_hk_updater_title)
    }

    override fun doOnNext(list: List<Lottery>) {
        Timber.v("LtoHK doOnNext, list size: ${list.size}")
    }

    override fun doOnComplete() {
        Timber.v("LtoHK doOnComplete")
    }

    override fun doOnError(throwable: Throwable) {
        Timber.v("LtoHK doOnError: $throwable")
    }
}