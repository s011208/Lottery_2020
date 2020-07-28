package bj4.dev.yhh.lottery.table.large

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.local.dao.LotteryEntity
import bj4.dev.yhh.lotterydata.repository.LotteryRepository

class LargeTableViewModel(private val repository: LotteryRepository) : ViewModel() {

    val mutableLotteryCategory: MutableLiveData<LotteryType> = MutableLiveData()

    fun searchByCategory(param: LotteryType) {
        mutableLotteryCategory.value = param
    }

    val lotteryObservable: LiveData<List<LotteryEntity>> =
        Transformations.switchMap(mutableLotteryCategory) { param ->
            repository.getFromLocalLiveData(param)
        }
}