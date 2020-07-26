package bj4.dev.yhh.lotterydata.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bj4.dev.yhh.lotterydata.local.dao.*

@Database(
    entities = [LotteryEntity::class, FinishEntity::class, UpdateLogEntity::class],
    version = 1
)
@TypeConverters(FieldConverter::class)
abstract class LotteryDatabase : RoomDatabase() {
    abstract fun lotteryDao(): LotteryDao
    abstract fun finishDao(): FinishDao
    abstract fun updateLogDao(): UpdateLogDao
}
