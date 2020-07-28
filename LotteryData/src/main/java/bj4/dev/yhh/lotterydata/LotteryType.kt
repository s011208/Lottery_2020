package bj4.dev.yhh.lotterydata

enum class LotteryType(val normalNumberCount: Int = 0, val specialNumberCount: Int = 0) {
    Lto(38, 8),
    LtoBig(49, 0),
    LtoHK(49, 0);
}