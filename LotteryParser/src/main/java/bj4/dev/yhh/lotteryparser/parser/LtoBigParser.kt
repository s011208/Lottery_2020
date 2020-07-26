package bj4.dev.yhh.lotteryparser.parser

import bj4.dev.yhh.lotteryparser.dao.Lottery
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LtoBigParser: Parser() {
    companion object {
        private const val URL = "https://www.pilio.idv.tw/ltobig/ServerB/list.asp?indexpage="

        private const val COLUMN_COUNT = 3

        private const val DATE_FORMATTER = "yyyy/MM/dd"
    }

    private val dateFormat = SimpleDateFormat(DATE_FORMATTER, Locale.getDefault())

    override fun getBaseUrl(): String = URL

    override fun parseInternal(url: String): List<Lottery> {
        val doc = Jsoup.connect(url).get()
        val elementTable = doc.select("table.auto-style1")
        val tds = elementTable.select("td")

        val rtn = ArrayList<Lottery>()

        var date: Long = 0
        var numberList: List<Int> = ArrayList()
        var specialNumberList: List<Int>
        for (i in COLUMN_COUNT until tds.size) {
            val value = tds[i].text()
            when {
                i % COLUMN_COUNT == 0 -> date = dateConverter(value)
                i % COLUMN_COUNT == 1 -> numberList = numberConverter(value)
                i % COLUMN_COUNT == 2 -> {
                    specialNumberList = specialNumberConverter(value)
                    rtn.add(Lottery(date, numberList, specialNumberList))
                }
            }
        }

        return rtn
    }

    override fun getLastDataDate(): Long = 1073257200000

    private fun dateConverter(date: String): Long {
        return dateFormat.parse(date.substring(0, date.length - 3).trim())?.time ?: 0L
    }

    private fun numberConverter(numbers: String): List<Int> {
        return numbers.split(",").map { it.trim().toInt() }.toList()
    }

    private fun specialNumberConverter(number: String): List<Int> {
        return ArrayList<Int>().apply { add(number.toInt()) }
    }
}