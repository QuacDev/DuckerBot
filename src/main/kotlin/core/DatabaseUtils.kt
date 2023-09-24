package core

import java.sql.PreparedStatement

object DatabaseUtils {
    fun getUser(id: Long) : User {
        if(Main.connection == null) return User(id, 0, 0L)

        val query = Main.connection!!.prepareStatement("SELECT * FROM users")
        val result = query.executeQuery()
        val users = mutableListOf<User>()
        while (result.next()) {
            val id = result.getString("userid").toLong()
            val points = result.getInt("points")
            val lastPointTimeStamp = result.getString("lastPointTimeStamp").toLong()

            users.add(User(id, points, lastPointTimeStamp))
        }

        for (user in users) {
            if(user.id == id) {
                return user
            }
        }
        return User(id, 0, 0L)
    }

    fun saveUser(user: User) {
        if(Main.connection == null) {
            println("Couldn't save user '${user.id}' to database because the connection is invalid.")
            return
        }

        val removeSql = " remove from users where userid='${user.id}'"

        val sql = " insert into users (userid, points, lastPointTimeStamp)" + " values (?, ?, ?)"

        val preparedStatement: PreparedStatement = Main.connection!!.prepareStatement(sql)
        preparedStatement.setString(1, user.id.toString())
        preparedStatement.setInt(2, user.points)
        preparedStatement.setString(3, user.lastPointTimeStamp.toString())

        preparedStatement.execute()
    }
}