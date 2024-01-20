package org.skefir.page

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.skefir.data.*
import org.skefir.elements.CalendarEventInfoPWElements
import org.skefir.elements.DataTablePW
import org.skefir.elements.TabControl
import org.skefir.util.addAllureTextAttachment
import org.skefir.util.convertDate
import org.skefir.util.convertDateTime
import java.time.LocalDate
import java.util.*

private val log = KotlinLogging.logger {}

class CalendarEventInfoPWPage(page: Page) : BasePagePW<CalendarEventInfoPWPage>(page) {


    protected inner class CalendarEventInfoElements : Elements(), CalendarEventInfoPWElements {
        override fun getPage(): Page {
            return page
        }
    }

    protected var eventInfoHelper = CalendarEventInfoElements()

    var tabControl = TabControl<CalendarEventInfoTab>(eventInfoHelper.getEventTabControl())

    private val eventHistoryTable = DataTablePW(
        eventInfoHelper.getEventHistoryTable(), "event-table-history", EnumSet.allOf(EventHistoryColumn::class.java)
    )

    @Step("Переходим на вкладку {0}")
    fun goToTab(tab: CalendarEventInfoTab): CalendarEventInfoPWPage {
        log.info("Переходим на вкладку {}", tab)
        tabControl.getTab(tab).click()
        return this
    }

    @Step("Проверяем что событие соответствует критериям {0}")
    fun checkEventInfo(eventFilteredCondition: EventFilteredCondition): CalendarEventInfoPWPage {
        log.info("Проверяем что событие соответствует критериям {}", eventFilteredCondition)
        checkImportance(eventFilteredCondition.importanceSet)
        checkCurrency(eventFilteredCondition.currenciesSet)
        checkDate(eventFilteredCondition.dateFilterOption)
        return this
    }

    @Step("Проверяем что событие имеет важность {0}")
    fun checkImportance(importanceSet: Set<ImportanceFilterOption>): CalendarEventInfoPWPage {
        log.info("Проверяем что событие имеет важность {}", importanceSet)
        Assertions.assertTrue(
            isOptionContains<ImportanceFilterOption>(importanceSet, eventInfoHelper.getEventImportance().textContent()),
            "Важность события ${
                eventInfoHelper.getEventImportance().textContent()
            } должна входить в выбранный фильтр ${importanceSet}"
        )
        return this
    }

    @Step("Проверяем что событие в валюте {0}")
    fun checkCurrency(currenciesSet: Set<Currencies>): CalendarEventInfoPWPage {
        log.info("Проверяем что событие в валюте {}", currenciesSet)
        Assertions.assertTrue(
            isOptionContains<Currencies>(currenciesSet, eventInfoHelper.getEventCurrency().textContent()),
            "Важность события ${
                eventInfoHelper.getEventCurrency().textContent()
            } должна входить в выбранный фильтр $currenciesSet"
        )
        return this
    }

    @Step("Проверяем что событие попадает в заданный интервал дат {0}")
    fun checkDate(dateFilterOptions: DateFilterOptions): CalendarEventInfoPWPage {
        log.info("Проверяем что событие попадает в заданный интервал дат {}", dateFilterOptions)
        val eventDate = convertDateTime(eventInfoHelper.getEventDate().textContent()).toLocalDate()
        Assertions.assertTrue(
            eventDate.isAfter(dateFilterOptions.beginPeriod.minusDays(1))
                    && eventDate.isBefore(dateFilterOptions.finishPeriod.plusDays(1)),
            "Дата события ${eventDate} должна находиться в периоде ${dateFilterOptions}"
        )
        return this
    }


    @Step("Получаем содержимое таблицы истории")
    fun printHistoryToLog() {
        log.info("Получаем содержимое таблицы истории")
        val reportSet = EnumSet.of(
            EventHistoryColumn.DATE, EventHistoryColumn.ACTUAL,
            EventHistoryColumn.FORECAST, EventHistoryColumn.PREVIOUS
        )
        val header = "| ${EventHistoryColumn.DATE.getTitle().padEnd(25)}             | " +
                 EventHistoryColumn.ACTUAL.getTitle().padStart(20) +
                " | ${EventHistoryColumn.FORECAST.getTitle().padStart(20)} " +
                "| ${EventHistoryColumn.PREVIOUS.getTitle().padStart(20)} |"

        val allureHistoryTable = StringBuilder(header)
        log.info(header)
        eventHistoryTable.getRowStream().map {
            eventHistoryTable.extractColumns(
                it,
                reportSet
            )
        }
            .takeWhile { e: Map<EventHistoryColumn, String> ->
                convertDate(
                    e[EventHistoryColumn.DATE] ?: ""
                ).isAfter(LocalDate.now().minusMonths(12))
            }
            .forEach { e: Map<EventHistoryColumn, String> ->
                val rowValue =
                    "| ${e[EventHistoryColumn.DATE]?.trim()?.padEnd(25)}             | " +
                            "${e[EventHistoryColumn.ACTUAL]?.trim()?.padStart(20)}" +
                            " | ${e[EventHistoryColumn.FORECAST]?.trim()?.padStart(20)} " +
                            "| ${e[EventHistoryColumn.PREVIOUS]?.trim()?.padStart(20)} |"
                log.info(rowValue)
                allureHistoryTable.append("\n")
                allureHistoryTable.append(rowValue)
            }
        addAllureTextAttachment("historyTable", allureHistoryTable.toString())
    }
}