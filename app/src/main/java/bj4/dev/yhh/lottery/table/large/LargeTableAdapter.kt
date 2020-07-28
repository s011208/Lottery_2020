package bj4.dev.yhh.lottery.table.large

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.UiUtilities
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.local.dao.LotteryEntity
import java.util.*
import kotlin.collections.ArrayList

internal class LargeTableAdapter : RecyclerView.Adapter<ViewHolder>() {
    val dataList = ArrayList<LotteryEntity>()
    var type: LotteryType = LotteryType.Lto
    private val dateFormatter = UiUtilities.makeDateFormat()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val container =
            inflater.inflate(R.layout.view_holder_large_table, parent, false) as LinearLayout

        val numberViewList = ArrayList<TextView>()
        val specialNumberViewList = ArrayList<TextView>()

        for (index in 0 until type.normalNumberCount) {
            container.addView(
                inflater.inflate(R.layout.text_view_large_table, container, false)
                    .also { numberViewList.add(it as TextView) }
            )
        }
        for (index in 0 until type.specialNumberCount) {
            container.addView(
                inflater.inflate(R.layout.text_view_large_table, container, false)
                    .also { specialNumberViewList.add(it as TextView) }
            )
        }

        return ViewHolder(container, numberViewList, specialNumberViewList)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = dateFormatter.format(Date(dataList[position].date))

        val numberList = ArrayList(dataList[position].number)
        for (index in holder.numberViewList.indices) {
            holder.numberViewList[index].text =
                if (numberList.contains(index + 1)) {
                    numberList.remove(index + 1)
                    "${index + 1}"
                } else {
                    ""
                }
        }

        val specialNumberList = ArrayList(dataList[position].specialNumber)
        for (index in holder.specialNumberViewList.indices) {
            holder.specialNumberViewList[index].text =
                if (specialNumberList.contains(index + 1)) {
                    specialNumberList.remove(index + 1)
                    "${index + 1}"
                } else {
                    ""
                }
        }
    }
}

internal class ViewHolder(
    val container: LinearLayout,
    val numberViewList: List<TextView>,
    val specialNumberViewList: List<TextView>
) : RecyclerView.ViewHolder(container) {
    val date = container.findViewById<TextView>(R.id.date)
}