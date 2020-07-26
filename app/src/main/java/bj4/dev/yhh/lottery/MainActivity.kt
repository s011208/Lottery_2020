package bj4.dev.yhh.lottery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bj4.dev.yhh.lotteryparser.parser.LtoBigParser
import bj4.dev.yhh.lotteryparser.parser.LtoHKParser
import bj4.dev.yhh.lotteryparser.parser.LtoParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            var page = 1
            LtoHKParser().parse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach {
                    Timber.tag("QQQQ").e("data: ${page++}")
                }
                .doOnComplete {
                    Timber.tag("QQQQ").e("complete")
                }
                .subscribe({
                    it.last().also { lottery ->
                        Timber.tag("QQQQ").v("date: ${lottery.date}, count: ${it.size}")
                    }
                }, {
                    Timber.tag("QQQQ").w(it)
                })
        }.start()
    }
}