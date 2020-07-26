package bj4.dev.yhh.lottery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.local.LotteryDatabase
import bj4.dev.yhh.lotterydata.local.dao.LotteryEntity
import bj4.dev.yhh.lotterydata.remote.LotteryRepository
import bj4.dev.yhh.lotteryparser.parser.LtoHKParser
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

        LotteryRepository(db).update(LotteryType.LtoBig)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Timber.tag("QQQQ").v("size: ${it.size}")
                }, {
                    Timber.tag("QQQQ").w(it)
                },
                {
                    Timber.tag("QQQQ").e("finish")
                }
            )
    }
}