package bj4.dev.yhh.lottery.table.large

import bj4.dev.yhh.lottery.table.DateCell
import bj4.dev.yhh.lottery.util.UiUtilities
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.local.dao.LotteryEntity
import java.util.*
import kotlin.collections.ArrayList

enum class RowType {
    Normal, Header, MonthlySubTotal, Footer
}

data class Row(
    val dateCell: DateCell,
    val cellList: List<Cell>,
    val rowType: RowType = RowType.Normal
) {

    companion object {
        fun make(entity: LotteryEntity, type: LotteryType): Row {
            val normalNumberCount = LotteryType.getNormalNumberCount(type)
            val specialNumberCount = LotteryType.getSpecialNumberCount(type)
            val separateSpecialNumber = LotteryType.isSeparateSpecialNumber(type)
            val dateCell = DateCell {
                UiUtilities.makeYMDDateFormat(this, entity.date)
            }
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
            return Row(dateCell, cellList, RowType.Normal)
        }

        fun makeHeader(type: LotteryType): Row {
            val normalNumberCount = LotteryType.getNormalNumberCount(type)
            val specialNumberCount = LotteryType.getSpecialNumberCount(type)
            val separateSpecialNumber = LotteryType.isSeparateSpecialNumber(type)
            val dateCell = DateCell { "" }
            val cellList: ArrayList<Cell> = ArrayList()
            for (index in 0 until normalNumberCount) {
                cellList.add(
                    Cell(
                        index + 1,
                        isSpecial = false,
                        display = true,
                        isHighLight = false,
                        isLastOfCategory = false
                    )
                )
            }
            if (separateSpecialNumber) {
                for (index in 0 until specialNumberCount) {
                    cellList.add(
                        Cell(
                            index + 1,
                            isSpecial = false,
                            display = true,
                            isHighLight = false,
                            isLastOfCategory = false
                        )
                    )
                }
            }
            return Row(dateCell, cellList, RowType.Header)
        }

        fun makeMonthlySubTotal(
            type: LotteryType,
            time: Long,
            numberList: ArrayList<Int>,
            specialNumberList: ArrayList<Int>,
            rowType: RowType = RowType.MonthlySubTotal
        ): Row {
            val normalNumberCount = LotteryType.getNormalNumberCount(type)
            val specialNumberCount = LotteryType.getSpecialNumberCount(type)
            val separateSpecialNumber = LotteryType.isSeparateSpecialNumber(type)
            val dateCell = if (rowType == RowType.MonthlySubTotal) {
                DateCell {
                    UiUtilities.makeYMDateFormat(
                        this,
                        time
                    )
                }
            } else {
                DateCell { "" }
            }
            val cellList: ArrayList<Cell> = ArrayList()
            if (!separateSpecialNumber) {
                numberList.addAll(specialNumberList)
            }

            for (index in 0 until normalNumberCount) {
                cellList.add(
                    Cell(
                        Collections.frequency(numberList, index + 1),
                        isSpecial = false,
                        display = true,
                        isHighLight = false,
                        isLastOfCategory = false
                    )
                )
            }
            if (separateSpecialNumber) {
                for (index in 0 until specialNumberCount) {
                    cellList.add(
                        Cell(
                            Collections.frequency(specialNumberList, index + 1),
                            isSpecial = false,
                            display = true,
                            isHighLight = false,
                            isLastOfCategory = false
                        )
                    )
                }
            }
            return Row(dateCell, cellList, rowType)
        }

        fun makeFooter(
            type: LotteryType, numberList: ArrayList<Int>,
            specialNumberList: ArrayList<Int>
        ): Row = makeMonthlySubTotal(type, 0, numberList, specialNumberList, RowType.Footer)
    }
}