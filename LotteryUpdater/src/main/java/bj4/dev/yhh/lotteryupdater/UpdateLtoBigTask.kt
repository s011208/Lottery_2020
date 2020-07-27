package bj4.dev.yhh.lotteryupdater

import android.content.Context
import androidx.work.WorkerParameters
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotteryparser.dao.Lottery
import timber.log.Timber

class UpdateLtoBigTask(context: Context, param: WorkerParameters) : UpdateTask(context, param) {
    override fun getNotificationId(): Int {
        return 900003
    }

    override fun getLotteryType(): LotteryType {
        return LotteryType.LtoBig
    }

    override fun getNotificationTitle(): String {
        return applicationContext.getString(R.string.notification_lto_big_updater_title)
    }

    override fun doOnNext(list: List<Lottery>) {
        Timber.v("LtoBig doOnNext, list size: ${list.size}")
    }

    override fun doOnComplete() {
        Timber.v("LtoBig doOnComplete")
    }

    override fun doOnError(throwable: Throwable) {
        Timber.v("LtoBig doOnError: $throwable")
    }
}