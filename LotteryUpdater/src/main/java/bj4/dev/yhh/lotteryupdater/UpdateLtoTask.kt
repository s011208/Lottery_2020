package bj4.dev.yhh.lotteryupdater

import android.content.Context
import androidx.work.WorkerParameters
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotteryparser.dao.Lottery
import timber.log.Timber

class UpdateLtoTask(context: Context, param: WorkerParameters): UpdateTask(context, param) {
    override fun getNotificationId(): Int {
        return 900001
    }

    override fun getLotteryType(): LotteryType {
        return LotteryType.Lto
    }

    override fun getNotificationTitle(): String {
        return applicationContext.getString(R.string.notification_lto_updater_title)
    }

    override fun doOnNext(list: List<Lottery>) {
        Timber.v("Lto doOnNext, list size: ${list.size}")
    }

    override fun doOnComplete() {
        Timber.v("Lto doOnComplete")
    }

    override fun doOnError(throwable: Throwable) {
        Timber.v("Lto doOnError: $throwable")
    }
}