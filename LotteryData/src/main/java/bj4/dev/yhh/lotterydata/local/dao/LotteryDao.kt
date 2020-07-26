package bj4.dev.yhh.lotterydata.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bj4.dev.yhh.lotterydata.local.entity.LotteryEntity
import io.reactivex.Observable

@Dao
interface LotteryDao {
    @Query("SELECT * from LotteryEntity where type=:type")
    fun getAll(type: String): Observable<List<LotteryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg lottery: LotteryEntity): List<Long>
}