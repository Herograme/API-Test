package br.com.herograme

import br.com.herograme.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    //configureSerialization()
    configureRouting()

    createDatabase()

}
