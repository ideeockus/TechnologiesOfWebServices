package org.lab4

import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
//        install(CallLogging) {
//            level = Level.INFO
//        }

        val bookDao = BookDAO() // Создание экземпляра DAO

        routing {
            route("/books") {
                get {
                    call.respond(bookDao.listAllBooks())
                }
//                get("{id}") {
//                    val id = call.parameters["id"]?.toIntOrNull()
//                    id?.let {
//                        val book = bookDao.getBook(it)
//                        if (book != null) call.respond(book) else call.respond(HttpStatusCode.NotFound)
//                    } ?: call.respond(HttpStatusCode.BadRequest, "Invalid ID")
//                }
//                post {
//                    val book = call.receive<BookLib>()
//                    val created = bookDao.insertBook(book)
//                    if (created) call.respond(HttpStatusCode.Created) else call.respond(HttpStatusCode.InternalServerError)
//                }
//                put("{id}") {
//                    val id = call.parameters["id"]?.toIntOrNull()
//                    id?.let {
//                        val book = call.receive<BookLib>()
//                        val updated = bookDao.updateBook(book)
//                        if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
//                    } ?: call.respond(HttpStatusCode.BadRequest, "Invalid ID")
//                }
//                delete("{id}") {
//                    val id = call.parameters["id"]?.toIntOrNull()
//                    id?.let {
//                        val deleted = bookDao.deleteBook(it)
//                        if (deleted) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
//                    } ?: call.respond(HttpStatusCode.BadRequest, "Invalid ID")
//                }
            }
        }
    }.start(wait = true)
}
