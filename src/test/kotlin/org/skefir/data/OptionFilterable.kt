package org.skefir.data

interface OptionFilterable {
    fun getTitle(): String

    //альтернативное название опции если по умолчанию равно нормальному названию
    fun getAltTitle(): String {
        return getTitle()
    }
}