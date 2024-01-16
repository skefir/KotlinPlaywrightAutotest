package org.skefir.data

enum class CalendarEventInfoTab(private val title: String) : ControlTabEntity {
    OVERVIEW("Overview"),
    CHART("Chart"),
    HISTORY("History"),
    WIDGET("Widget");

    override fun getTitle(): String  = title
}