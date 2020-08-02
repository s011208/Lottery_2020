package bj4.dev.yhh.lottery.table.large

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import bj4.dev.yhh.lottery.util.RxLiveDataMapAsync
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.repository.LotteryRepository
import java.util.*
import kotlin.collections.ArrayList

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
            val lotteryType = mutableLotteryCategory.value!!
            val rtn = ArrayList<Row>()
            rtn.add(Row.makeHeader(lotteryType))
            val monthlyNormalList = ArrayList<Int>()
            val monthlySpecialList = ArrayList<Int>()
            val totalNormalList = ArrayList<Int>()
            val totalSpecialList = ArrayList<Int>()
            val currentCalendar = Calendar.getInstance().apply {
                timeInMillis = firstOrNull()?.date ?: System.currentTimeMillis()
                set(Calendar.HOUR, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.MILLISECOND, 0)
            }
            forEach { lotteryEntity ->
                val itemCalendar = Calendar.getInstance().apply {
                    timeInMillis = lotteryEntity.date
                    set(Calendar.HOUR, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                if (currentCalendar.get(Calendar.YEAR) == itemCalendar.get(Calendar.YEAR) &&
                    currentCalendar.get(Calendar.MONTH) == itemCalendar.get(Calendar.MONTH)
                ) {
                    monthlyNormalList.addAll(lotteryEntity.number)
                    monthlySpecialList.addAll(lotteryEntity.specialNumber)
                } else {
                    rtn.add(
                        Row.makeMonthlySubTotal(
                            lotteryType,
                            currentCalendar.timeInMillis,
                            monthlyNormalList,
                            monthlySpecialList
                        )
                    )
                    currentCalendar.timeInMillis = itemCalendar.timeInMillis
                    monthlyNormalList.clear()
                    monthlySpecialList.clear()
                    monthlyNormalList.addAll(lotteryEntity.number)
                    monthlySpecialList.addAll(lotteryEntity.specialNumber)
                }
                totalNormalList.addAll(lotteryEntity.number)
                totalSpecialList.addAll(lotteryEntity.specialNumber)
                rtn.add(Row.make(lotteryEntity, lotteryType))
            }
            rtn.add(
                Row.makeMonthlySubTotal(
                    lotteryType,
                    currentCalendar.timeInMillis,
                    monthlyNormalList,
                    monthlySpecialList
                )
            )
            rtn.add(Row.makeFooter(lotteryType, totalNormalList, totalSpecialList))
            return@RxLiveDataMapAsync rtn
        }).also { disposedList.add(it) }.mapAsync()

    override fun onCleared() {
        super.onCleared()
        disposedList.forEach { it.dispose() }
    }
}