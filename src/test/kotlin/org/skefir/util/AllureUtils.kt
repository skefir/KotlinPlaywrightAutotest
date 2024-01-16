package org.skefir.util

import io.qameta.allure.Allure

fun addAllureTextAttachment(title: String, body: String) =
    Allure.addAttachment(title, body)