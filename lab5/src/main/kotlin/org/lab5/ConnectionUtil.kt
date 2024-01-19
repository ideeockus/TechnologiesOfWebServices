package org.lab5

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

object ConnectionUtil {
    private const val JDBC_URL = "jdbc:postgresql://localhost:5432/lab_db"
    private const val JDBC_USER = "itmo_user"
    private const val JDBC_PASSWORD = "pass"

    val connection: Connection?
        get() {
            var connection: Connection? = null
            try {
                connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)
            } catch (ex: SQLException) {
                Logger.getLogger(ConnectionUtil::class.java.name).log(
                    Level.SEVERE, null,
                    ex
                )
            }
            return connection
        }
}
