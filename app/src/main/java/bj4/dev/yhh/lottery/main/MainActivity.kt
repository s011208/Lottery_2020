package bj4.dev.yhh.lottery.main

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.UiUtilities
import bj4.dev.yhh.lottery.main.dialog.DisplayType
import bj4.dev.yhh.lottery.main.dialog.DisplayTypeDialogFragment
import bj4.dev.yhh.lottery.main.dialog.LotteryTypeDialogFragment
import bj4.dev.yhh.lottery.settings.SettingsActivity
import bj4.dev.yhh.lottery.table.large.LargeTableFragment
import bj4.dev.yhh.lotterydata.LotteryType
import timber.log.Timber


class MainActivity : AppCompatActivity(), LotteryTypeDialogFragment.Callback,
    DisplayTypeDialogFragment.Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    override fun onResume() {
        super.onResume()
        initSettings()
    }

    private fun initView() {
        switchFragment(LotteryType.Lto)
    }


    private fun initSettings() {
        UiUtilities.setOrientation(
            this,
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        )
    }

    private fun switchFragment(lotteryType: LotteryType) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LargeTableFragment.make(lotteryType))
            .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.switchLotteryType -> {
                showLotteryTypeDialog()
                true
            }
            R.id.switchDisplayType -> {
                showDisplayTypeDialog()
                true
            }
            R.id.switchTableType -> true
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLotteryTypeDialog() {
        LotteryTypeDialogFragment().show(
            supportFragmentManager,
            LotteryTypeDialogFragment::class.java.name
        )
    }

    private fun showDisplayTypeDialog() {
        DisplayTypeDialogFragment().show(
            supportFragmentManager,
            DisplayTypeDialogFragment::class.java.name
        )
    }

    override fun onLotteryTypeSelected(lotteryType: LotteryType) {
        Timber.v("onLotteryTypeSelected: $lotteryType")
        switchFragment(lotteryType)
    }

    override fun onDisplayTypeSelected(displayType: DisplayType) {
        Timber.v("onDisplayTypeSelected: $displayType")
    }
}