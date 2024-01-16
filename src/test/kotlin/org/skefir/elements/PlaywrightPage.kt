package org.skefir.elements

import com.microsoft.playwright.Page

interface PlaywrightPage {
    fun getPage():Page
}