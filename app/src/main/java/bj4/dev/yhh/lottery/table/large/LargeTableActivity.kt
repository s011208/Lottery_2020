package bj4.dev.yhh.lottery.table.large

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.UiUtilities
import bj4.dev.yhh.lotterydata.LotteryType
import kotlinx.android.synthetic.main.activity_update_log.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LargeTableActivity : AppCompatActivity() {

    private val viewModel: LargeTableViewModel by viewModel()
    private val adapter: LargeTableAdapter = LargeTableAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_large_table)

        initSettings()
        initViews()
        initViewModels()
    }

    private fun initSettings() {
        UiUtilities.setOrientation(this, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    private fun initViewModels() {
        viewModel.searchByCategory(LotteryType.Lto)
        viewModel.lotteryObservable.observe(this, Observer {
            adapter.dataList.clear()
            adapter.dataList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}