package bj4.dev.yhh.lotterydata.local.dao

import androidx.room.*
import bj4.dev.yhh.lotterydata.LotteryType
import io.reactivex.Observable

@Dao
interface UpdateLogDao {
    @Query("SELECT * from UpdateLogEntity")
    fun getAll(): Observable<List<UpdateLogEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg lottery: UpdateLogEntity): List<Long>
}

@Entity
data class UpdateLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: LotteryType,
    val timeStamp: Long,
    val message: String = ""
)