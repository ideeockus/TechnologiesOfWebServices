package org.lab6

import java.sql.*


class BookDAO {
    private var jdbcConnection: Connection? = null

    @Throws(SQLException::class)
    protected fun connect() {
        if (jdbcConnection == null || jdbcConnection!!.isClosed) {
            jdbcConnection = ConnectionUtil.connection;
        }
    }

    @Throws(SQLException::class)
    protected fun disconnect() {
        if (jdbcConnection != null && !jdbcConnection!!.isClosed) {
            jdbcConnection!!.close()
        }
    }

    @Throws(SQLException::class)
    fun insertBook(book: BookLib?): Boolean {
        val sql = "INSERT INTO books (title, author, genre, year, isbn) VALUES (?, ?, ?, ?, ?)"
        connect()

        if (book == null) {
            throw SQLException("book cannot be null")
        }
        if (jdbcConnection == null) {
            throw SQLException("jdb connection is null")
        }

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setString(1, book.title)
        statement.setString(2, book.author)
        statement.setString(3, book.genre)
        statement.setInt(4, book.year)
        statement.setString(5, book.isbn)

        val rowInserted = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowInserted
    }

    @Throws(SQLException::class)
    fun listAllBooks(): List<BookLib> {
        val listBook: MutableList<BookLib> = ArrayList()

        val sql = "SELECT * FROM books"

        connect()

        val statement = jdbcConnection!!.createStatement()
        val resultSet = statement.executeQuery(sql)

        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val title = resultSet.getString("title")
            val author = resultSet.getString("author")
            val genre = resultSet.getString("genre")
            val year = resultSet.getInt("year")
            val isbn = resultSet.getString("isbn")

            val book = BookLib(id, title, author, genre, isbn, year)
            listBook.add(book)
        }

        resultSet.close()
        statement.close()

        disconnect()

        return listBook
    }

    @Throws(SQLException::class)
    fun getBook(id: Int): BookLib? {
        var book: BookLib? = null
        val sql = "SELECT * FROM books WHERE id = ?"

        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setInt(1, id)

        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val title = resultSet.getString("title")
            val author = resultSet.getString("author")
            val genre = resultSet.getString("genre")
            val year = resultSet.getInt("year")
            val isbn = resultSet.getString("isbn")

            book = BookLib(id, title, author, genre, isbn, year)
        }

        resultSet.close()
        statement.close()

        disconnect()

        return book
    }


    @Throws(SQLException::class)
    fun updateBook(book: BookLib): Boolean {
        val sql = "UPDATE books SET title = ?, author = ?, genre = ?, year = ?, isbn = ? WHERE id = ?"
        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setString(1, book.title)
        statement.setString(2, book.author)
        statement.setString(3, book.genre)
        statement.setInt(4, book.year)
        statement.setString(5, book.isbn)
        statement.setInt(6, book.id)

        val rowUpdated = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowUpdated
    }

    @Throws(SQLException::class)
    fun deleteBook(id: Int): Boolean {
        val sql = "DELETE FROM books WHERE id = ?"
        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setInt(1, id)

        val rowDeleted = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowDeleted
    }
}

