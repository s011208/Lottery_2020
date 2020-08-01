package bj4.dev.yhh.lottery.table.large

import bj4.dev.yhh.lottery.table.DateCell
import bj4.dev.yhh.lottery.util.UiUtilities
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.local.dao.LotteryEntity
import java.util.*
import kotlin.collections.ArrayList

data class Row(val dateCell: DateCell, val cellList: List<Cell>) {

    companion object {
        fun make(entity: LotteryEntity, type: LotteryType): Row {
            val normalNumberCount = LotteryType.getNormalNumberCount(type)
            val specialNumberCount = LotteryType.getSpecialNumberCount(type)
            val separateSpecialNumber = LotteryType.isSeparateSpecialNumber(type)
            val dateCell = DateCell(
                UiUtilities.makeDateFormat().format(Date(entity.date))
            )
            val cellList: ArrayList<Cell> = ArrayList()
            for (index in 1..normalNumberCount) {
                if (entity.number.contains(index)) {
                    cellList.add(
                        Cell(
                            index,
                            isSpecial = false,
                            display = true,
                            isHighLight = false,
                            isLastOfCategory = false
                        )
                    )
                } else {
                    val isSpecial = !separateSpecialNumber && entity.specialNumber.contains(index)
                    cellList.add(
                        Cell(
                            index,
                            isSpecial = isSpecial,
                            display = isSpecial,
                            isHighLight = false,
                            isLastOfCategory = false
                        )
                    )
                }
            }
            for (index in 1..specialNumberCount) {
                if (entity.specialNumber.contains(index)) {
                    cellList.add(
                        Cell(
                            index,
                            isSpecial = true,
                            display = true,
                            isHighLight = false,
                            isLastOfCategory = false
                        )
                    )
                } else {
                    cellList.add(
                        Cell(
                            index,
                            isSpecial = true,
                            display = false,
                            isHighLight = false,
                            isLastOfCategory = false
                        )
                    )
                }
            }
            return Row(dateCell, cellList)
        }
    }
}