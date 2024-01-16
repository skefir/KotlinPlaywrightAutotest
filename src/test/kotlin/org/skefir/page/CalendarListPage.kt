package org.skefir.page

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import mu.KotlinLogging
import org.skefir.data.CalendarTableColumn
import org.skefir.data.Currencies
import org.skefir.data.DateFilterOptions
import org.skefir.data.ImportanceFilterOption
import org.skefir.elements.CalendarListElements
import org.skefir.elements.ColumnGroupTable
import java.nio.file.Paths
import java.util.*


private val log = KotlinLogging.logger {}
class CalendarListPage(page: Page) : BasePagePW<CalendarListPage>(page) {
    protected inner class CalendarPageElements() : Elements(), CalendarListElements {
        override fun getPage(): Page {
            return page
        }
    }

    protected var calendarHelper = CalendarPageElements()

    private val calendarMainDataTable: ColumnGroupTable<CalendarTableColumn> = ColumnGroupTable(
        calendarHelper.getMainTable(), "ec-table", EnumSet.allOf(
            CalendarTableColumn::class.java
        )
    )

    @Step("Устанавливаем фильтр валют значениями {0}")
    fun setCurrenciesFilter(currenciesSet: Set<Currencies>): CalendarListPage {
        log.info("Устанавливаем фильтр валют значениями {}", currenciesSet)
        return setFilterCheckboxGroup(calendarHelper.getCurrenciesFilter(), currenciesSet)
    }
//
//    @Step("Set date filter with {0}")
//    fun setDateFilter(dateFilterOptions: DateFilterOptions): CalendarPage? {
////        log.info("Устанавливаем фильтр валют занчениями {}", dateFilterOptions)
//        return setFilterRadio(calendarHelper.getDateFilter(), dateFilterOptions)
//    }

    @Step("Устанавливаем фильтр важности занчениями {0}")
    fun setImportanceFilter(importanceSet: Set<ImportanceFilterOption>): CalendarListPage {
        log.info("Устанавливаем фильтр важности занчениями {}", importanceSet)
        return setFilterCheckboxGroup(calendarHelper.getImportanceFilter(), importanceSet)
    }

    @Step("Set date filter with {0}")
    fun setDateFilter(dateFilterOptions: DateFilterOptions): CalendarListPage {
        page.screenshot(
            Page.ScreenshotOptions()
                .setPath(Paths.get("screenshot.png"))
                .setFullPage(true)
        )
        log.info("Устанавливаем фильтр валют занчениями {}", dateFilterOptions)
        return setFilterRadio(calendarHelper.getDateFilter(), dateFilterOptions)
    }

    @Step("Заходим в событие с порядковым номером {0}")
    fun enterToEventByNumber(eventNumber: Int): CalendarEventInfoPWPage {
        log.info("Заходим в событие с порядковым номером {}", eventNumber)
        calendarMainDataTable.getColumn(calendarMainDataTable.getRowByNumber(eventNumber), CalendarTableColumn.EVENT)
            .locator("a").click()
        return CalendarEventInfoPWPage(page)
    }
}