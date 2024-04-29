package org.skefir.test

import com.microsoft.playwright.Browser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.skefir.conf.BaseTestPWExtension
import org.skefir.conf.TestConfig
import org.skefir.data.*
import org.skefir.page.CalendarListPage
import java.util.*


@ExtendWith(BaseTestPWExtension::class)
class CalendarPWTests {

    @Test

    fun calendarFilterTest() {
        val eventFilteredCondition = EventFilteredCondition(
            EnumSet.of(ImportanceFilterOption.MEDIUM),
            DateFilterOptions.CURRENT_MONTH, EnumSet.of(Currencies.CHF)
        )
        val browser = TestConfig.getBrowser()
        val userAgent =TestConfig.configProperties.getString("browser.useAgent")
        val context = browser.newContext(
            Browser.NewContextOptions().setUserAgent(userAgent)

        )
        val page = context.newPage()
        val url = TestConfig.configProperties.getString("urls.metaUrl")
        page.navigate(url)
        val calendarPage = CalendarListPage(page)
        calendarPage.setCurrenciesFilter(eventFilteredCondition.currenciesSet)
            .setDateFilter(eventFilteredCondition.dateFilterOption)
            .setImportanceFilter(eventFilteredCondition.importanceSet)
            .enterToEventByNumber(1)
            .checkEventInfo(eventFilteredCondition)
            .goToTab(CalendarEventInfoTab.HISTORY)
            .printHistoryToLog()
    }
}
