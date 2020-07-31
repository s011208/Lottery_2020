package bj4.dev.yhh.lottery.util

import android.app.Activity
import androidx.annotation.MainThread
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


object UiUtilities {

    // ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    fun setOrientation(activity: Activity, orientation: Int) {
        activity.requestedOrientation = orientation
    }

    fun makeDateFormat() = SimpleDateFormat("MM / dd / yyyy", Locale.getDefault())
}

object LiveDataUtil {

    fun <T, X, Y, Z> zip(
        source1: LiveData<X>,
        source2: LiveData<Y>,
        source3: LiveData<Z>,
        block: (X?, Y?, Z?) -> T
    ): LiveData<T> {
        return MediatorLiveData<T>().apply {
            addSource(source1) {
                value = block(source1.value, source2.value, source3.value)
            }
            addSource(source2) {
                value = block(source1.value, source2.value, source3.value)
            }
            addSource(source3) {
                value = block(source1.value, source2.value, source3.value)
            }
        }
    }

    fun <T, K, R> LiveData<T>.combineWith(
        liveData: LiveData<K>,
        block: (T?, K?) -> R
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = block(this.value, liveData.value)
        }
        result.addSource(liveData) {
            result.value = block(this.value, liveData.value)
        }
        return result
    }
}

class RxLiveDataMapAsync<X, Y>(
    private val source: LiveData<Y>,
    private val onChange: Y.() -> X,
    private val onStart: (() -> Unit)? = null,
    private val onComplete: (() -> Unit)? = null,
    private val onError: (Throwable.() -> Unit)? = null
) :
    MediatorLiveData<X>() {
    private val compositeDisposable = CompositeDisposable()

    fun mapAsync(): LiveData<X> {
        return MediatorLiveData<X>().apply {
            addSource(source) {
                compositeDisposable.add(Observable.fromCallable { onChange(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { onStart?.invoke() }
                    .subscribe(
                        {
                            postValue(it)
                        },
                        {
                            onError?.invoke(it)
                        },
                        {
                            onComplete?.invoke()
                        }
                    )
                )
            }
        }
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}