package bj4.dev.yhh.lottery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.local.LotteryDatabase
import bj4.dev.yhh.lotterydata.local.entity.LotteryEntity
import bj4.dev.yhh.lotteryparser.parser.LtoBigParser
import bj4.dev.yhh.lotteryparser.parser.LtoHKParser
import bj4.dev.yhh.lotteryparser.parser.LtoParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            LotteryDatabase::class.java, "lottery"
        ).build()

        Thread {
            var page = 1
            LtoHKParser().parse().subscribeOn(Schedulers.io())
                .doOnNext {
                    it.forEach {lottery ->
                        val rtn = db.lotteryDao().insert(LotteryEntity(lottery.date, LotteryType.LtoHK, lottery.number, lottery.specialNumber))
                        Timber.tag("QQQQ").i("insert result: $rtn")
                        if (rtn.contains(-1)) {
                            throw IllegalStateException("exist")
                        }
                    }
                }
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