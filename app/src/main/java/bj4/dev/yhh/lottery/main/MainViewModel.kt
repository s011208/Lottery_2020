package bj4.dev.yhh.lottery.main

import androidx.lifecycle.ViewModel
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.repository.LotteryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class MainViewModel(private val repository: LotteryRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val updateMap = HashMap<LotteryType, AtomicBoolean>()

    fun update(lotteryType: LotteryType, onFailed: Throwable.() -> Unit, onComplete: () -> Unit) {
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
                    .subscribe(
                        {
                        }, {
                            if (it is LotteryRepository.LoadFinishedException) {
                                onComplete()
                            } else {
                                Timber.w(it, "failed to update")
                                it.onFailed()
                            }
                            isUpdating.set(false)
                        }, {
                            onComplete()
                            isUpdating.set(false)
                        }
                    )
            )
        } else {
            Timber.v("$lotteryType is updating, ignore")
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}