package br.com.herograme

import br.com.herograme.plugins.configureRouting
import br.com.herograme.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()

    createDatabase()

}
