package br.com.herograme.plugins

import br.com.herograme.ResetTable
import br.com.herograme.getAllUsers
import br.com.herograme.insertUser
import br.com.herograme.requests.User
import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.DriverManager
import java.sql.SQLException

fun Application.configureRouting() {
    routing {
        put("") {
            println("put")
            var sucessCount = 0
            for (i in 1..10_000){

                if(insertUser(User(Nome = "Nome$i", Email = "exemple$i@exemple.com"))){
                    sucessCount += 1
                }
            }
            call.respond(HttpStatusCode.OK,"Write Sucess:$sucessCount")
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
