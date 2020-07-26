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
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {
    private val repository: LotteryRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        repository.update(LotteryType.LtoBig)
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                {
//                    Timber.tag("QQQQ").v("size: ${it.size}")
//                }, {
//                    Timber.tag("QQQQ").w(it)
//                },
//                {
//                    Timber.tag("QQQQ").e("finish")
//                }
//            )
    }
}