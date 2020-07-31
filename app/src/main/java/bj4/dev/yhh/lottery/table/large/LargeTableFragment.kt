package bj4.dev.yhh.lottery.table.large

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lotterydata.LotteryType
import kotlinx.android.synthetic.main.activity_update_log.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LargeTableFragment : Fragment() {

    private val viewModel: LargeTableViewModel by viewModel()
    private val adapter: LargeTableAdapter = LargeTableAdapter()

    companion object {
        private const val ARG_LOTTERY_TYPE = "lottery_type"

        fun make(lotteryType: LotteryType) : LargeTableFragment {
            return LargeTableFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOTTERY_TYPE, lotteryType.name)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_large_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModels()
    }

    private fun initViewModels() {
        viewModel.searchByCategory(LotteryType.valueOf(requireArguments().getString(ARG_LOTTERY_TYPE)!!))
        viewModel.lotteryObservable.observe(viewLifecycleOwner, Observer {
            adapter.dataList.clear()
            adapter.dataList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}