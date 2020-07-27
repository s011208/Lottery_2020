package bj4.dev.yhh.lotterydata.repository

import bj4.dev.yhh.lotterydata.local.LotteryDatabase

class UpdateLogRepository(private val database: LotteryDatabase) {

    fun getAll() = database.updateLogDao().getAllFromLiveData()
}