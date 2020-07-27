package bj4.dev.yhh.lottery.settings.updatelog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lotterydata.local.dao.UpdateLogEntity
import kotlinx.android.synthetic.main.activity_update_log.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class UpdateLogActivity: AppCompatActivity() {

    private val viewModel: UpdateLogViewModel by viewModel()

    private val adapter: UpdateLogAdapter = UpdateLogAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_log)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.log.observe(this, Observer {
            adapter.list.clear()
            adapter.list.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }
}

private class UpdateLogAdapter: RecyclerView.Adapter<UpdateLogAdapterViewHolder>(){
    val list = ArrayList<UpdateLogEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateLogAdapterViewHolder {
        return UpdateLogAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_update_log, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UpdateLogAdapterViewHolder, position: Int) {
        holder.time.text = Date(list[position].timeStamp).toString()
        holder.type.text = list[position].type.name
    }
}

private class UpdateLogAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val time: TextView = view.findViewById(R.id.time)
    val type: TextView = view.findViewById(R.id.type)
}