package bj4.dev.yhh.lottery

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import bj4.dev.yhh.lottery.settings.SettingsActivity
import bj4.dev.yhh.lottery.table.large.LargeTableFragment


class MainActivity : AppCompatActivity() {
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
        switchFragment()
    }


    private fun initSettings() {
        UiUtilities.setOrientation(this, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    private fun switchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LargeTableFragment())
            .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.switchLotteryType -> true
            R.id.switchTableType -> true
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLotteryTypeDialog() {

    }
}