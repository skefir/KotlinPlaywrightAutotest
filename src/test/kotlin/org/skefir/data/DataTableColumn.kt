package org.skefir.data

interface DataTableColumn {
    fun getTitle(): String

    fun getColumnByTitle(title: String) : DataTableColumn

}