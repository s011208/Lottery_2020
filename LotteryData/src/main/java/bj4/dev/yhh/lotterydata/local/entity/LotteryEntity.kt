package bj4.dev.yhh.lotterydata.local.entity

import androidx.room.Entity
import bj4.dev.yhh.lotterydata.LotteryType

@Entity(primaryKeys = ["date", "type"])
data class LotteryEntity(
    val date: Long,
    val type: LotteryType,
    val number: List<Int>,
    val specialNumber: List<Int>
)