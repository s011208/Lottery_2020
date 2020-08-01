package bj4.dev.yhh.lottery.main

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.main.dialog.DisplayTypeDialogFragment
import bj4.dev.yhh.lottery.main.dialog.LotteryTypeDialogFragment
import bj4.dev.yhh.lottery.main.dialog.TableTypeDialogFragment
import bj4.dev.yhh.lottery.settings.SettingsActivity
import bj4.dev.yhh.lottery.table.large.LargeTableFragment
import bj4.dev.yhh.lottery.util.UiUtilities
import bj4.dev.yhh.lotterydata.LotteryType
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.lang.IllegalStateException


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSettings()
        setContentView(R.layout.activity_main)
        initViewModels()
    }

    private fun initViewModels() {
        viewModel.showProgressBar.observe(this, Observer {
            progressBar.isVisible = it
        })

        viewModel.toastEmitter.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.fragmentInfoLiveData.observe(this, Observer { info ->
            Timber.v("info: $info")
            refreshFragment()
            when (info.lotteryType) {
                LotteryType.Lto -> {
                    setTitle(R.string.lottery_type_lto)
                }
                LotteryType.LtoBig -> {
                    setTitle(R.string.lottery_type_lto_big)
                }
                LotteryType.LtoHK -> {
                    setTitle(R.string.lottery_type_lto_hk)
                }
                else -> throw IllegalStateException("Unexpected lottery type: ${info.lotteryType}")
            }
        })
    }

    private fun initSettings() {
        UiUtilities.setOrientation(
            this,
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        )
    }

    private fun refreshFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, LargeTableFragment())
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
            R.id.switchTableType -> {
                showTableTypeDialog()
                true
            }
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.update -> {
                viewModel.update()
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

    private fun showTableTypeDialog() {
        TableTypeDialogFragment().show(
            supportFragmentManager,
            TableTypeDialogFragment::class.java.name
        )
    }
}