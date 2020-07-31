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