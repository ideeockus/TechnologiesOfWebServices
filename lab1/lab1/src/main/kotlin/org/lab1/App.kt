package org.lab1;

import javax.xml.ws.Endpoint

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        val url = "http://localhost:8080/BookService"
        Endpoint.publish(url, BookService())
    }
}
