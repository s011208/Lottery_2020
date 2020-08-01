package bj4.dev.yhh.lotterydata

enum class LotteryType(
    val normalNumberCount: Int = 0,
    val specialNumberCount: Int = 0,
    val separateSpecialNumber: Boolean
) {
    Lto(38, 8, true),
    LtoBig(49, 49, false),
    LtoHK(49, 49, false);

    companion object {
        fun getNormalNumberCount(type: LotteryType): Int {
            return when (type) {
                Lto -> Lto.normalNumberCount
                LtoBig -> LtoBig.normalNumberCount
                LtoHK -> LtoHK.normalNumberCount
                else -> throw IllegalStateException("Unexpected type")
            }
        }

        fun getSpecialNumberCount(type: LotteryType): Int {
            return when (type) {
                Lto -> Lto.specialNumberCount
                LtoBig -> LtoBig.specialNumberCount
                LtoHK -> LtoHK.specialNumberCount
                else -> throw IllegalStateException("Unexpected type")
            }
        }

        fun isSeparateSpecialNumber(type: LotteryType): Boolean {
            return when (type) {
                Lto -> Lto.separateSpecialNumber
                LtoBig -> LtoBig.separateSpecialNumber
                LtoHK -> LtoHK.separateSpecialNumber
                else -> throw IllegalStateException("Unexpected type")
            }
        }
    }
}