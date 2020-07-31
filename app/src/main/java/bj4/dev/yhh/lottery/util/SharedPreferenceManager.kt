package bj4.dev.yhh.lottery.util

import android.content.Context
import bj4.dev.yhh.lottery.main.dialog.DisplayType
import bj4.dev.yhh.lottery.main.dialog.TableType
import bj4.dev.yhh.lotterydata.LotteryType

class SharedPreferenceManager(context: Context) {

    companion object {
        private const val SHARED_PREFERENCE_NAME = "pref"

        private const val KEY_DISPLAY_TYPE = "KEY_DISPLAY_TYPE"
        private const val KEY_LOTTERY_TYPE = "KEY_LOTTERY_TYPE"
        private const val KEY_TABLE_TYPE = "KEY_TABLE_TYPE"
    }

    private val sharedPreferences = context.applicationContext.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    fun getDisplayType(): DisplayType =
        DisplayType.values()[sharedPreferences.getInt(KEY_DISPLAY_TYPE, 0)]

    fun setDisplayType(type: DisplayType) =
        sharedPreferences.edit().putInt(KEY_DISPLAY_TYPE, type.ordinal).apply()

    fun getLotteryType(): LotteryType =
        LotteryType.values()[sharedPreferences.getInt(KEY_LOTTERY_TYPE, 0)]

    fun setLotteryType(type: LotteryType) =
        sharedPreferences.edit().putInt(KEY_LOTTERY_TYPE, type.ordinal).apply()

    fun getTableType(): TableType = TableType.values()[sharedPreferences.getInt(KEY_TABLE_TYPE, 0)]

    fun setTableType(type: TableType) =
        sharedPreferences.edit().putInt(KEY_LOTTERY_TYPE, type.ordinal).apply()

}