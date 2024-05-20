package br.com.herograme

import br.com.herograme.requests.User
import java.sql.DriverManager
import java.sql.SQLException

val database =
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

fun insertUser(user: User):Boolean{
    //println(user.Nome)
    val query = database.prepareStatement("INSERT INTO 'users'(name,email) VALUES (?,?)")
    query.setString(1,user.Nome)
    query.setString(2,user.Email)
    query.executeUpdate()
    return true
}

fun getAllUsers():List<User>{
    val users = mutableListOf<User>()
    try {
        val result = database.createStatement().executeQuery("SELECT * FROM 'users'")

        while (result.next()){
            val user = User (
                Id = result.getInt("id"),
                Nome = result.getString("name"),
                Email = result.getString("email")
            )
            users.add(user)
        }
        println("teste${users.size}")
        return users
    }catch (e: SQLException){
        println("erro:$e")
    }
    return users
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