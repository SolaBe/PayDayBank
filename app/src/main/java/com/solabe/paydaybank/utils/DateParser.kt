package com.solabe.paydaybank.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.*
import java.time.format.DateTimeFormatter

fun String.dateStringToSeconds() : Long {
    return try {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT))
            .atStartOfDay().toEpochSecond(ZoneOffset.UTC)
    } catch (e: Exception) {
        return try {
            LocalDateTime.parse(this, DateTimeFormatter.ofPattern(DateUtils.DATETIME_FORMAT))
                .toEpochSecond(ZoneOffset.UTC)
        } catch (e: Exception) {
            0L
        }
    }
}

fun String.startEndPeriodFromMonthYear() : Pair<String?, String?> {
    return try {
        val yearMonth = YearMonth.parse(this, DateTimeFormatter.ofPattern(DateUtils.MONTH_YEAR_FORMAT))
        val dateStart = yearMonth.atDay(1).atStartOfDay()
        val dateEnd = yearMonth.atEndOfMonth().atTime(23, 59, 59)
        Pair(dateStart.format(DateTimeFormatter.ofPattern(DateUtils.DATETIME_FORMAT)),
            dateEnd.format(DateTimeFormatter.ofPattern(DateUtils.DATETIME_FORMAT)))
    } catch (e: Exception) {
        Pair(null, null)
    }
}

object DateUtils {

    fun getMonthYearList(topEdge: LocalDate, bottomEdge: LocalDate, cb: (List<String>) -> (Unit)) {
        GlobalScope.launch(Dispatchers.Default) {
            val monthYearList = mutableListOf<String>()
            var date = bottomEdge.withDayOfMonth(1)
            var end = topEdge.withDayOfMonth(1)
            do {
                monthYearList.add(date.format(DateTimeFormatter.ofPattern(MONTH_YEAR_FORMAT)))
                date = if (date.month == Month.DECEMBER) {
                    date.plusYears(1).withMonth(Month.JANUARY.value)
                } else {
                    date.plusMonths(1)
                }
            } while (!date.isEqual(end))
            withContext(Dispatchers.Main) {
                cb(monthYearList)
            }
        }
    }

    const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val DATE_FORMAT = "M/d/yyyy"
    const val MONTH_YEAR_FORMAT = "MMM yyyy"
}