package org.skefir.elements

import com.microsoft.playwright.Locator
import org.skefir.data.ControlTabEntity

class TabControl<T : ControlTabEntity>(val rootElement: Locator) {
    fun getTab(tab: T): Locator = rootElement.locator("li:has-text('${tab.getTitle()}')")
}