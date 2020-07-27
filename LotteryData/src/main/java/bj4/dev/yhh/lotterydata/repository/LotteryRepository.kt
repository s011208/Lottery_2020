package bj4.dev.yhh.lotterydata.repository

import bj4.dev.yhh.lotterydata.LotteryType
import bj4.dev.yhh.lotterydata.local.LotteryDatabase
import bj4.dev.yhh.lotterydata.local.dao.FinishEntity
import bj4.dev.yhh.lotterydata.local.dao.LotteryEntity
import bj4.dev.yhh.lotterydata.local.dao.UpdateLogEntity
import bj4.dev.yhh.lotteryparser.dao.Lottery
import bj4.dev.yhh.lotteryparser.parser.LtoBigParser
import bj4.dev.yhh.lotteryparser.parser.LtoHKParser
import bj4.dev.yhh.lotteryparser.parser.LtoParser
import bj4.dev.yhh.lotteryparser.parser.Parser
import io.reactivex.Observable
import java.lang.Exception
import java.lang.IllegalArgumentException

class LotteryRepository(private val database: LotteryDatabase) {

    fun getFromLocal(type: LotteryType): Observable<List<Lottery>> =
        database.lotteryDao().getAll(type.name).map {
            it.map { lotteryEntity ->
                Lottery(
                    lotteryEntity.date,
                    lotteryEntity.number,
                    lotteryEntity.specialNumber
                )
            }
        }

    fun update(type: LotteryType): Observable<List<Lottery>> {
        val hasFinish = database.finishDao().getAll(type.name).blockingFirst().isNotEmpty()

        return getParser(type).parse()
            .doOnSubscribe {
                database.updateLogDao()
                    .insert(UpdateLogEntity(type = type, timeStamp = System.currentTimeMillis()))
            }
            .doOnNext { list ->
                list.forEach { lottery ->
                    val result = database.lotteryDao().insert(
                        LotteryEntity(
                            lottery.date,
                            type,
                            lottery.number,
                            lottery.specialNumber
                        )
                    )
                    if (result.contains(-1) && hasFinish) {
                        throw LoadFinishedException(
                            type
                        )
                    }
                }
            }
            .doOnComplete {
                database.finishDao().insert(FinishEntity(type))
            }
    }

    private fun getParser(type: LotteryType): Parser {
        return when (type) {
            LotteryType.LtoHK -> LtoHKParser()
            LotteryType.Lto -> LtoParser()
            LotteryType.LtoBig -> LtoBigParser()
            else -> throw IllegalArgumentException("Unexpected LotteryType: $type")
        }
    }

    class LoadFinishedException(val type: LotteryType) : Exception()
}