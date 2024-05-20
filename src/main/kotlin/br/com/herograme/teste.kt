package br.com.herograme

import br.com.herograme.requests.User
import java.sql.DriverManager
import java.sql.SQLException

var database =
    DriverManager.getConnection("jdbc:sqlite:database.db") ?: throw SQLException("Não foi possivel criar")

fun createDatabase(){
    try {
        database.createStatement().execute("""
           CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                email TEXT NOT NULL
           )
       """.trimIndent())
    } catch (e:Exception){
        println("erro: $e")
    }
}

fun insertUsers(users:List<User>) {
    //println(user.Nome)
    database.use { connection ->
            connection.autoCommit = false
            val sql = "INSERT INTO users (name,email) VALUES(?,?)"
            connection.prepareStatement(sql).use {statement ->
                for (user in users) {
                    statement.setString(1,user.Nome)
                    statement.setString(2,user.Email)
                    statement.addBatch()
                }
                statement.executeBatch()
            }
            connection.commit()
            true
        }
}

fun getAllUsers():List<User> {
    val users = mutableListOf<User>()
    try {
        val result = database.createStatement().executeQuery("SELECT * FROM 'users'")

        while (result.next()) {
            val user = User(
                Id = result.getInt("id"),
                Nome = result.getString("name"),
                Email = result.getString("email")
            )
            users.add(user)
        }
        println("teste${users.size}")
        return users
    } catch (e: SQLException) {
        if (e.errorCode == 0) {
            database =
                DriverManager.getConnection("jdbc:sqlite:database.db") ?: throw SQLException("Não foi possivel criar")


            val result = database.createStatement().executeQuery("SELECT * FROM 'users'")

            while (result.next()) {
                val user = User(
                    Id = result.getInt("id"),
                    Nome = result.getString("name"),
                    Email = result.getString("email")
                )
                users.add(user)

            }

        }
        return users
    }
}

fun ResetTable(){
   try {
       database.createStatement().use {
           it.execute("DROP TABLE IF EXISTS users")
       }
       createDatabase()
   } catch (e:SQLException){
       e.printStackTrace()
   }
}