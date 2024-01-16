package org.skefir.data

enum class Currencies(private val currencyName: String) : OptionFilterable {
    AUD("Australian Dollar"),
    BRL("Brazilian real"),
    CAD("Canadian dollar"),
    CHF("Swiss frank"),
    CNY("Chinese yuan"),
    EUR("Euro"),
    GBP("Pound sterling"),
    HKD("Hong Kong dollar"),
    INR("Indian rupee"),
    JPY("Japanese yen"),
    KRW("South Korean won"),
    MXN("Mexican peso"),
    NOK("Norwegian Krone"),
    NZD("New Zealand dollar"),
    SEK("Swedish krona"),
    SGD("Singapore dollar"),
    USD("US dollar"),
    ZAR("South African rand");

    override fun getTitle(): String = "$name - $currencyName"

    override fun getAltTitle(): String = "$name, $currencyName"

    override fun toString(): String = "$name-${getTitle()}(${getAltTitle()})"

}