package bj4.dev.yhh.lotterydata.local

import androidx.room.TypeConverter
import bj4.dev.yhh.lotterydata.LotteryType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FieldConverter {

    @TypeConverter
    fun fromLotteryType(value: LotteryType): String = value.name

    @TypeConverter
    fun toLotteryType(value: String): LotteryType = enumValueOf(value)

    @TypeConverter
    fun toIntList(value: String): List<Int> =
        Gson().fromJson(value, object : TypeToken<ArrayList<Int>>() {}.type)


    @TypeConverter
    fun fromIntList(list: List<Int>): String =
        Gson().toJson(list)
}