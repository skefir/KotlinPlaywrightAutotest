package org.skefir.data

data class EventFilteredCondition(
    var importanceSet: Set<ImportanceFilterOption>,
    var dateFilterOption: DateFilterOptions,
    var currenciesSet: Set<Currencies>)
