package org.skefir.test

import com.microsoft.playwright.Browser
import com.microsoft.playwright.Page
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.skefir.conf.BaseTestPWExtension
import org.skefir.conf.TestConfig
import org.skefir.data.*
import org.skefir.page.CalendarListPage
import java.nio.file.Paths
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
        val userAgent =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36"
        val context = browser.newContext(
            Browser.NewContextOptions()
                .setLocale("de-DE")
                .setTimezoneId("Europe/Berlin").setUserAgent(userAgent)

        )
        val page = context.newPage()
        val url = TestConfig.configProperties.getString("urls.metaUrl")
        println(url)
        page.navigate(url)
//        page.waitForURL(url)
        page.screenshot(
            Page.ScreenshotOptions()
                .setPath(Paths.get("screenshot0.png"))
                .setFullPage(true)
        )
        println(page.url())
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