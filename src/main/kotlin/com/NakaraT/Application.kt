package com.NakaraT

import com.NakaraT.data.getDbRouting
import com.NakaraT.login.configureLoginRouting
import com.NakaraT.plugins.*
import com.NakaraT.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    getDbRouting()
}
