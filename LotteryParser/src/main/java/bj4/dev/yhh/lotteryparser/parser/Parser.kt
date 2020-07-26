package bj4.dev.yhh.lotteryparser.parser

import bj4.dev.yhh.lotteryparser.dao.Lottery
import io.reactivex.Observable

abstract class Parser {

    private var currentPage: Int = 1

    fun parse(): Observable<List<Lottery>> {
        return Observable.create<List<Lottery>> { emitter ->
            do {
                val list: ArrayList<Lottery> = ArrayList()
                try {
                    list.addAll(parseInternal(getUrl()))
                    emitter.onNext(list)
                    ++currentPage
                } catch (e: Throwable) {
                    emitter.onError(e)
                }
            } while (list.last().date != getLastDataDate())
            emitter.onComplete()
        }
    }

    private fun getUrl(): String = "${getBaseUrl()}$currentPage"

    internal abstract fun getBaseUrl(): String

    internal abstract fun parseInternal(url: String): List<Lottery>

    internal abstract fun getLastDataDate(): Long
}