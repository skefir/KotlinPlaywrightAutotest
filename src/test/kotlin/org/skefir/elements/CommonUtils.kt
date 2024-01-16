package org.skefir.elements

import com.microsoft.playwright.Locator
import org.skefir.data.OptionFilterable

interface CommonUtils {
    fun getFilterOptions(rootFilterElement: Locator): Locator = rootFilterElement.locator("li:visible")

    fun getFilterCheckBox(filterOptionElement: Locator): Locator = filterOptionElement.locator("input[type='checkbox']")

    fun getFilterOptionLabel(filterOptionsElement: Locator): Locator = filterOptionsElement.locator("label")

    fun getFilterOption(rootFilterElement: Locator, option: OptionFilterable): Locator =
        rootFilterElement.locator("li label:has-text('${option.getTitle()}')")

}