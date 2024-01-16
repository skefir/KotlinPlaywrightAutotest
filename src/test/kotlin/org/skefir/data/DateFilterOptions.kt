package org.skefir.data

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters.firstDayOfMonth
import java.time.temporal.TemporalAdjusters.lastDayOfMonth

enum class DateFilterOptions(private val title: String,val beginPeriod: LocalDate, val finishPeriod: LocalDate) : OptionFilterable {
    //TODO нужно уточнить как начинается неделя в целевой системе возможно с воскресенья
    CURRENT_WEEK("Current week"
        , LocalDate.now().with(DayOfWeek.MONDAY)
        , LocalDate.now().with(DayOfWeek.SUNDAY)),
    PREVIOUS_WEEK("Previous week"
        , LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY)
        , LocalDate.now().minusWeeks(1).with(DayOfWeek.SUNDAY)),
    NEXT_WEEK("Next week"
        , LocalDate.now().plusWeeks(1).with(DayOfWeek.MONDAY)
        , LocalDate.now().plusWeeks(1).with(DayOfWeek.SUNDAY)),
    CURRENT_MONTH("Current month"
        , LocalDate.now().with(firstDayOfMonth())
        , LocalDate.now().with(lastDayOfMonth())),
    PREVIOUS_MONTH("Previous month"
        , LocalDate.now().minusMonths(1).with(firstDayOfMonth())
        , LocalDate.now().minusMonths(1).with(lastDayOfMonth())),
    NEXT_MONTH("Next month"
        , LocalDate.now().plusMonths(1).with(firstDayOfMonth())
        , LocalDate.now().plusMonths(1).with(lastDayOfMonth()));

    override fun getTitle(): String {
        return title
    }
    override fun toString(): String {
        return "$name -($beginPeriod, $finishPeriod)"
    }
}