package org.skefir.elements

import com.microsoft.playwright.Locator

interface CalendarListElements : PlaywrightPage {
    fun getCalendarRoot(): Locator = getPage().locator("#calendarContainer")

    fun getFilterArea(): Locator = getCalendarRoot().locator("#economicCalendarFilter")

    fun getCurrenciesFilter(): Locator = getFilterArea().locator("ul#economicCalendarFilterCurrency")

    fun getDateFilter(): Locator = getFilterArea().locator("ul#economicCalendarFilterDate")

    fun getImportanceFilter(): Locator = getFilterArea().locator("ul#economicCalendarFilterImportance")

    fun getMainTable(): Locator =
        getCalendarRoot().locator("xpath=.//div[@class='ec-table' and .//div[@id='economicCalendarTable']]")

}