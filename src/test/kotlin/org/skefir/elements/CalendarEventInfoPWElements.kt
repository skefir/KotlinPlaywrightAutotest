package org.skefir.elements

import com.microsoft.playwright.Locator

interface CalendarEventInfoPWElements : PlaywrightPage {

    private fun getEventRoot(): Locator = getPage().locator("#eventContentPanel")

    fun getEventCurrency():Locator = getEventRoot().locator(".economic-calendar__event-header-currency")


    fun getEventImportance(): Locator = getEventRoot().locator(".event-table__importance")


    fun getEventDate(): Locator = getEventRoot().locator("td#actualValueDate")


    fun getEventTabControl(): Locator = getEventRoot().locator("ul#calendar-tabs")


    fun getEventHistoryTable(): Locator = getEventRoot().locator("#tab_content_history")

}