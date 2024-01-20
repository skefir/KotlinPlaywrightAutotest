package org.skefir.conf

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.FileBasedConfiguration
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Parameters


object TestConfig {
    var configProperties: Configuration
    private var playwright: Playwright


    init {
        val builder = FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration::class.java)
            .configure(
                Parameters().properties()
                    .setFileName(System.getProperty("autotest.config.file", "autotest.properties"))
            )
        configProperties = builder.configuration
        playwright = Playwright.create()
    }

    fun getBrowser(): Browser {
        return playwright.chromium().launch(
            BrowserType.LaunchOptions().setHeadless(
                configProperties.getBoolean("selenide.browser.headless", false)
            )
        )
    }
}