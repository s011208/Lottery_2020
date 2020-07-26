package bj4.dev.yhh.lottery.app

import androidx.room.Room
import bj4.dev.yhh.lotterydata.local.LotteryDatabase
import bj4.dev.yhh.lotterydata.remote.LotteryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            LotteryDatabase::class.java, "lottery"
        ).build()
    }

    single { LotteryRepository(get()) }
}