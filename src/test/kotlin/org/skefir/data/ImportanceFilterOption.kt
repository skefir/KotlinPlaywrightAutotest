package org.skefir.data

import java.util.*

enum class ImportanceFilterOption(private val title: String) : OptionFilterable {
    HOLIDAYS("Holidays"), LOW("Low"), MEDIUM("Medium"), HIGH("High");

    override fun getTitle(): String {
        return title
    }

    override fun getAltTitle(): String {
        return title.uppercase(Locale.getDefault())
    }

    override fun toString(): String {
        return name + "-" + title + "(" + getAltTitle() + ")"
    }
}