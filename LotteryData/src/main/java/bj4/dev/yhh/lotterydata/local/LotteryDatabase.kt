package bj4.dev.yhh.lotterydata.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bj4.dev.yhh.lotterydata.local.dao.FinishDao
import bj4.dev.yhh.lotterydata.local.dao.FinishEntity
import bj4.dev.yhh.lotterydata.local.dao.LotteryDao
import bj4.dev.yhh.lotterydata.local.dao.LotteryEntity

@Database(entities = [LotteryEntity::class, FinishEntity::class], version = 1)
@TypeConverters(FieldConverter::class)
abstract class LotteryDatabase : RoomDatabase() {
    abstract fun lotteryDao(): LotteryDao
    abstract fun finishDao(): FinishDao
}
