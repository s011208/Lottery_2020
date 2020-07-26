package bj4.dev.yhh.lotteryparser.dao

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lottery(val date: Long, val number: List<Int>, val specialNumber: List<Int>) :
    Parcelable