package br.com.herograme.plugins

import br.com.herograme.ResetTable
import br.com.herograme.getAllUsers
import br.com.herograme.insertUsers
import br.com.herograme.requests.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        put("") {
            val users = mutableListOf<User>()

            for (i in 1..10_000){
                users.add(User(Nome = "Nome$i", Email = "exemple$i@exemple.com"))
            }

            insertUsers(users)
            call.respond(HttpStatusCode.OK,"Inserido com sucesso")
        }
        get("") {
            println("get")
            val users = getAllUsers()

            call.respond(HttpStatusCode.OK,"Users Read ${users.size}")
        }
        delete(""){
            ResetTable()

            call.respond(HttpStatusCode.OK)
        }
    }




}
