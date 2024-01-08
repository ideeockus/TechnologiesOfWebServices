package org.lab1

import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class PostgreSQLDAO {
    val all: List<BookLib>
        get() {
            val books: MutableList<BookLib> = ArrayList<BookLib>()
            try {
                ConnectionUtil.connection.use { connection ->
                    val stmt: Statement? = connection?.createStatement()
                    val rs = stmt?.executeQuery("select * from songs") ?: return emptyList()
                    while (rs.next()) {
                        val id = rs.getInt("id")
                        val title = rs.getString("title")
                        val author = rs.getString("author")
                        val genre = rs.getString("genre")
                        val isbn = rs.getString("isbn")
                        val year = rs.getInt("year")

                        val book: BookLib = BookLib(id, title, author, genre, isbn, year)
                        books.add(book)
                    }
                }
            } catch (ex: SQLException) {
                Logger.getLogger(PostgreSQLDAO::class.java.name).log(
                    Level.SEVERE,
                    null, ex
                )
            }
            return books
        }
}
