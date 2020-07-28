package bj4.dev.yhh.lottery

import android.app.Activity
import java.text.SimpleDateFormat
import java.util.*

object UiUtilities {

    // ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    fun setOrientation(activity: Activity, orientation: Int) {
        activity.requestedOrientation = orientation
    }

    fun makeDateFormat() = SimpleDateFormat("MM / dd / yyyy", Locale.getDefault())
}