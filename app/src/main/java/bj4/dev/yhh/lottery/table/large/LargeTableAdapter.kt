package bj4.dev.yhh.lottery.table.large

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bj4.dev.yhh.lottery.R

internal class LargeTableAdapter : RecyclerView.Adapter<ViewHolder>() {
    val dataList = ArrayList<Row>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val container =
            inflater.inflate(R.layout.view_holder_large_table, parent, false) as LinearLayout

        val numberViewList = ArrayList<TextView>()

        for (index in 0 until 49) {
            container.addView(
                inflater.inflate(R.layout.text_view_large_table, container, false)
                    .also { numberViewList.add(it as TextView) }
            )
        }
        return ViewHolder(container, numberViewList)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = dataList[position]
        holder.date.text = row.dateCell.date(holder.date.context)

        for (index in holder.viewList.indices) {
            if (index > row.cellList.lastIndex) {
                holder.viewList[index].visibility = View.INVISIBLE
            } else {
                with(holder.viewList[index]) {
                    val cell = row.cellList[index]
                    text = cell.number.toString()
                    visibility = View.VISIBLE
                    if (cell.display) {
                        if (cell.isSpecial) {
                            setTextColor(Color.BLUE)
                        } else {
                            setTextColor(Color.BLACK)
                        }
                    } else {
                        setTextColor(Color.WHITE)
                    }
                }
            }
        }
    }
}

internal class ViewHolder(
    container: LinearLayout,
    val viewList: List<TextView>
) : RecyclerView.ViewHolder(container) {
    val date: TextView = container.findViewById(R.id.date)
}