package org.skefir.elements

import com.microsoft.playwright.Locator
import org.skefir.data.DataTableColumn
import org.skefir.page.sequence

/**
 *
 * @param rootElement - doomelement - содержащий в себе таблицу
 * @param classPrefix - префикс css классов таблицы
 * @param columns - ограниченный набор колонок таблицы
 */
open class DataTablePW<E : DataTableColumn>(val rootElement: Locator, val classPrefix: String, val columns: Set<E>) {


    protected var columnsNumbers: MutableMap<E, Int> = mutableMapOf()


    protected open fun getColumnNumber(column: E): Int {
        require(columns.contains(column)) { "Illegal column - $column" }
        return columnsNumbers.getOrPut(column)
        {
            rootElement.locator(
                "xpath=.//div[@class='${classPrefix}__header']" +
                        "//div[contains(@class,'${classPrefix}__')" +
                        " and .//text()='${column.getTitle()}']/preceding-sibling::div"
            ).count() + 1
        }

    }

    open fun getRowByNumber(rowNumber: Int): Locator {
        val rows = getRows()
        require(rows.count() > rowNumber) { "Illegal row - $rowNumber of ${rows.count()}" }
        return getRows().nth(rowNumber)
    }

    private fun getRows(): Locator {
        return rootElement.locator("div.${classPrefix}__item")
    }

    open fun getColumn(rowElement: Locator, column: E): Locator {
        return rowElement.locator("div[class*='${classPrefix}__']:nth-child(${getColumnNumber(column)})")
    }

    private fun getBottomArea(): Locator {
        return rootElement.locator("div[class*='table__bottom']")
    }

    private fun getRowsOnPage(paginatorElement: Locator): Sequence<Locator> {
        paginatorElement.click()
        return getRows().sequence().map { (_, l) -> l }
    }

    fun getRowStream(): Sequence<Locator> {
        //получаем колекцию страниц пейджинатора
        val pagerCollection = getBottomArea().locator("a")
        //если страница 1 то колеция пуста добавляем туда 1 элемент страницы
        val paginationStream =
            if (pagerCollection.count() > 0) pagerCollection.sequence() else sequence { 0 to getBottomArea() }
        //конвертируем поток пейджей в поток строк
        return paginationStream.flatMap { (_, l) ->
            getRowsOnPage(l)
        }
    }

    fun extractColumns(rowElement: Locator, columnsSet: Set<E>): Map<E, String> {
        val rowColumns = rowElement.locator("div[class*='${classPrefix}__']")
        return columnsSet.map { it to rowColumns.nth(getColumnNumber(it) - 1).textContent() }.toMap()
    }
}