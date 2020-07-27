package bj4.dev.yhh.lottery.app

import androidx.room.Room
import bj4.dev.yhh.lottery.settings.updatelog.UpdateLogViewModel
import bj4.dev.yhh.lotterydata.local.LotteryDatabase
import bj4.dev.yhh.lotterydata.repository.LotteryRepository
import bj4.dev.yhh.lotterydata.repository.UpdateLogRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            LotteryDatabase::class.java, "lottery"
        ).build()
    }

    single { LotteryRepository(get()) }
    single { UpdateLogRepository(get()) }
}

val viewModule = module {
    viewModel { UpdateLogViewModel(get()) }
}