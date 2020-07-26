package bj4.dev.yhh.lotterydata.local.dao

import androidx.room.*
import bj4.dev.yhh.lotterydata.LotteryType
import io.reactivex.Observable

@Dao
interface FinishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg type: FinishEntity): List<Long>

    @Query("SELECT * from FinishEntity where type=:type")
    fun getAll(type: String): Observable<List<FinishEntity>>
}

@Entity
data class FinishEntity(@PrimaryKey val type: LotteryType)

