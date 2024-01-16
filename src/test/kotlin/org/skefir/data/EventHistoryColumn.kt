package org.skefir.data

enum class EventHistoryColumn(private val title: String) : DataTableColumn {
    DATE("Date (GMT)"),
    REFERENCE("Reference"),
    ACTUAL("Actual"),
    FORECAST("Forecast"),
    PREVIOUS("Previous");

    override fun getTitle(): String = title

    override fun getColumnByTitle(title: String): DataTableColumn =
        EventHistoryColumn.values().first { it.title == title }

}