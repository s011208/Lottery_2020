package bj4.dev.yhh.lottery.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bj4.dev.yhh.lottery.R
import bj4.dev.yhh.lottery.main.type.DisplayType
import bj4.dev.yhh.lottery.main.type.FragmentInfo
import bj4.dev.yhh.lottery.main.type.TableType
import bj4.dev.yhh.lottery.util.LiveDataUtil
import bj4.dev.yhh.lottery.util.SharedPreferenceManager
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.repository.LotteryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class MainViewModel(private val repository: LotteryRepository) : ViewModel() {
    enum class Event {
        ShowTop, ShowBottom
    }

    private val sharedPreferenceManager: SharedPreferenceManager by inject(SharedPreferenceManager::class.java)

    private val compositeDisposable = CompositeDisposable()

    private val updateMap = HashMap<LotteryType, AtomicBoolean>()

    val showProgressBar = MutableLiveData<Boolean>()

    val toastEmitter = MutableLiveData<Int>()

    val activityEvent = MutableLiveData<Event>()

    fun showTop() {
        activityEvent.value = Event.ShowTop
    }

    fun showBottom() {
        activityEvent.value = Event.ShowBottom
    }

    val lotteryType =
        MutableLiveData<LotteryType>().apply { value = sharedPreferenceManager.getLotteryType() }

    fun setLotteryType(type: LotteryType) {
        lotteryType.value = type
        sharedPreferenceManager.setLotteryType(type)
    }

    val tableType =
        MutableLiveData<TableType>().apply { value = sharedPreferenceManager.getTableType() }

    fun setTableType(type: TableType) {
        tableType.value = type
        sharedPreferenceManager.setTableType(type)
    }

    val displayType =
        MutableLiveData<DisplayType>().apply { value = sharedPreferenceManager.getDisplayType() }

    fun setDisplayType(type: DisplayType) {
        displayType.value = type
        sharedPreferenceManager.setDisplayType(type)
    }

    val fragmentInfoLiveData = LiveDataUtil.zip(
        lotteryType,
        tableType,
        displayType
    ) { lotteryType, tableType, displayType ->
        FragmentInfo(lotteryType!!, tableType!!, displayType!!)
    }

    fun update() {
        val lotteryType: LotteryType = lotteryType.value!!
        var isUpdating = updateMap[lotteryType]
        if (isUpdating == null) {
            isUpdating = AtomicBoolean(false)
            updateMap[lotteryType] = isUpdating
        }
        if (isUpdating.compareAndSet(false, true)) {
            compositeDisposable.add(
                repository.update(lotteryType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        showProgressBar.value = true
                    }
                    .subscribe(
                        {},
                        {
                            if (it is LotteryRepository.LoadFinishedException) {
                                toastEmitter.value = R.string.activity_main_toast_update_complete
                            } else {
                                Timber.w(it, "failed to update")
                                toastEmitter.value = R.string.activity_main_toast_update_failure
                            }
                            isUpdating.set(false)
                            showProgressBar.value = false
                        }, {
                            toastEmitter.value = R.string.activity_main_toast_update_complete
                            isUpdating.set(false)
                            showProgressBar.value = false
                        }
                    )
            )
        } else {
            Timber.v("$lotteryType is updating, ignore")
            toastEmitter.value = R.string.activity_main_toast_update_ignore
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}