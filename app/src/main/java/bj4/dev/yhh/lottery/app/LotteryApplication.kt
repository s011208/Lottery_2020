package bj4.dev.yhh.lottery.app

import android.app.Application
import timber.log.Timber

class LotteryApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}