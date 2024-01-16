package org.skefir.conf

import io.qameta.allure.Step
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class BaseTestPWExtension : BeforeAllCallback, BeforeEachCallback, AfterEachCallback {




    @Throws(Exception::class)
    override fun beforeAll(context: ExtensionContext?) {

    }

    @Throws(Exception::class)
    override fun afterEach(context: ExtensionContext?) {

    }

    @Throws(Exception::class)
    override fun beforeEach(context: ExtensionContext?) {

    }
}