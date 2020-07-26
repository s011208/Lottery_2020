package bj4.dev.yhh.lotteryupdater

import android.content.Context
import androidx.work.WorkerParameters
import bj4.dev.yhh.lotterydata.LotteryType

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
}