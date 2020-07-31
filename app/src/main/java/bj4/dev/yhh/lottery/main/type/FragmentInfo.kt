package bj4.dev.yhh.lottery.main.type

import bj4.dev.yhh.lotterydata.LotteryType

data class FragmentInfo(
    val lotteryType: LotteryType,
    val tableType: TableType,
    val displayType: DisplayType
)