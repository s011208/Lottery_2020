package bj4.dev.yhh.lottery.table.large

data class Cell(
    val number: Int,
    val isSpecial: Boolean,
    val display: Boolean,
    val isHighLight: Boolean,
    val isLastOfCategory: Boolean
)