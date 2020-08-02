package bj4.dev.yhh.lottery.table.large

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.main.MainViewModel
import kotlinx.android.synthetic.main.activity_update_log.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LargeTableFragment : Fragment() {

    private val viewModel: LargeTableViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private val adapter: LargeTableAdapter = LargeTableAdapter()

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
        viewModel.searchByCategory(mainViewModel.lotteryType.value!!)
        viewModel.lotteryObservable.observe(viewLifecycleOwner, Observer {
            adapter.dataList.clear()
            adapter.dataList.addAll(it)
            adapter.notifyDataSetChanged()
        })
        mainViewModel.activityEvent.observe(viewLifecycleOwner, Observer {
            when (it!!) {
                MainViewModel.Event.ShowTop -> {
                    recyclerView.scrollToPosition(0)
                }
                MainViewModel.Event.ShowBottom -> {
                    recyclerView.scrollToPosition(adapter.dataList.lastIndex)
                }
            }
        })
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }
}