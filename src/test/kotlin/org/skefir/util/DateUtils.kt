package org.skefir.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


private val dataFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
    private val dataTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm O", Locale.ENGLISH)

    fun convertDate(strDate: String): LocalDate {
        return LocalDate.parse(strDate, dataFormatter)
    }

    fun convertDateTime(strDate: String): LocalDateTime {
        return LocalDateTime.parse(strDate, dataTimeFormatter)
    }
