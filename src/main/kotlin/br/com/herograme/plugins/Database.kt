package br.com.herograme.plugins

import br.com.herograme.requests.User
import io.ktor.server.application.*
import java.sql.DriverManager
import java.sql.SQLException

fun Application.Database() {
    val database =
        DriverManager.getConnection("jdbc:sqlite:database.db") ?: throw SQLException("Não foi possivel criar")
    try {
       database.createStatement().execute("""
           CREATE TABLE IF NOT EXISTS users (
                id INTERGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL
           )
       """.trimIndent())
    } catch (e:Exception){
        println("erro: $e")
    }

    fun insertUser(user:User):Boolean{
        return try {
            val query = database.prepareStatement("""INSERT INTO users(name,email)""")
            query.setString(1,user.Nome)
            query.setString(2,user.Email)
            query.executeUpdate()
            println("user:${user.Nome}")
            true
        } catch (e:SQLException){
            false
        }
    }

    fun getAllUsers():List<User>{
        val users = mutableListOf<User>()
        try {
            val result = database.createStatement().executeQuery("""SELECT * FROM users""")

            while (result.next()){
                val user = User (
                    Id = result.getInt("id"),
                    Nome = result.getString("name"),
                    Email = result.getString("email")
                )
                users.add(user)
            }
            return users
        }catch (e:SQLException){
            println("erro:$e")
        }
            return users
    }


}

