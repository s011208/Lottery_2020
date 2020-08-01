package bj4.dev.yhh.lottery.table.large

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import bj4.dev.yhh.lottery.util.RxLiveDataMapAsync
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.repository.LotteryRepository

class LargeTableViewModel(private val repository: LotteryRepository) : ViewModel() {

    private val disposedList = ArrayList<RxLiveDataMapAsync<*, *>>()

    private val mutableLotteryCategory: MutableLiveData<LotteryType> = MutableLiveData()

    fun searchByCategory(param: LotteryType) {
        mutableLotteryCategory.value = param
    }

    val lotteryObservable =
        RxLiveDataMapAsync(Transformations.switchMap(mutableLotteryCategory) { param ->
            repository.getFromLocalLiveData(param)
        }, {
            val rtn = ArrayList<Row>()
            forEach { lotteryEntity ->
                rtn.add(Row.make(lotteryEntity, mutableLotteryCategory.value!!))
            }
            return@RxLiveDataMapAsync rtn
        }).also { disposedList.add(it) }.mapAsync()

    override fun onCleared() {
        super.onCleared()
        disposedList.forEach { it.dispose() }
    }
}