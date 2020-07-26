package bj4.dev.yhh.lotterydata.local.dao

import androidx.room.*
import bj4.dev.yhh.lotterydata.LotteryType
import io.reactivex.Observable

@Dao
interface LotteryDao {
    @Query("SELECT * from LotteryEntity where type=:type")
    fun getAll(type: String): Observable<List<LotteryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg lottery: LotteryEntity): List<Long>
}

@Entity(primaryKeys = ["date", "type"])
data class LotteryEntity(
    val date: Long,
    val type: LotteryType,
    val number: List<Int>,
    val specialNumber: List<Int>
)