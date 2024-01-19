//package org.lab4.client
//
//
//
//import io.ktor.client.*
//import io.ktor.client.engine.cio.*
//import io.ktor.serialization.kotlinx.json.*
//import io.ktor.client.request.*
//import io.ktor.client.statement.*
//import io.ktor.http.*
//import kotlinx.coroutines.runBlocking
//import org.lab4.BookLib
//import java.util.*
//
//
//
//class BookServiceClientCLI(
//    private val client: HttpClient
//) {
//    fun run() {
//        val scanner = Scanner(System.`in`)
//
//        while (true) {
//            println("Выберите действие: c - Добавить книгу, r - Получить книгу, u - Обновить книгу, d - Удалить книгу, e - Выйти")
//            val choice = scanner.nextLine()
//
//            when (choice) {
//                "c" -> {
//                    // Логика добавления книги
//                    val book = inputBook()
//                    if (book == null) {
//                        println("Проверьте корректность введенных данных")
//                        continue
//                    }
//                    runBlocking {
//                        createBook(book)
//                    }
//                }
//                "r" -> {
//                    // Логика получения книги
//                    println("Введите ID книги:")
//                    val id = scanner.nextLine().toInt()
//                    runBlocking {
//                        getBook(id)
//                    }
//                }
//                "u" -> {
//                    // Логика обновления книги
//                    println("Введите ID книги для обновления:")
//                    val id = scanner.nextLine().toInt()
//
//                    val updatedBook = inputBook()
//                    if (updatedBook != null) {
//                        updatedBook.id = id
//                    } else continue;
//
//                    runBlocking {
//                        val success = updateBook(id, updatedBook)
//                        if (success) println("Книга обновлена успешно") else println("Ошибка при обновлении книги")
//                    }
//                }
//                "d" -> {
//                    // Логика удаления книги
//                    println("Введите ID книги для удаления:")
//                    val id = scanner.nextLine().toInt()
//                    runBlocking {
//                        val success = deleteBook(id)
//                        if (success) println("Книга удалена успешно") else println("Ошибка при удалении книги")
//                    }
//                }
//                "e" -> {
//                    println("Выход из программы...")
//                    return
//                }
//                else -> {
//                    println("Некорректный ввод. Попробуйте снова.")
//                }
//            }
//
//        }
//    }
//
//    private fun inputBook(): BookLib? {
//        val scanner = Scanner(System.`in`)
//
//        // Запрашиваем данные для обновления
//        println("Введите название книги:")
//        val title = scanner.nextLine()
//        println("Введите автора книги:")
//        val author = scanner.nextLine()
//        println("Введите жанр книги:")
//        val genre = scanner.nextLine()
//        println("Введите isbn книги:")
//        val isbn = scanner.nextLine()
//        println("Введите год издания книги:")
//        val yearInput = scanner.nextLine()
//        val year: Int;
//
//        try {
//            year = yearInput?.toInt() ?: throw NumberFormatException("Год не введен")
//        } catch (e: NumberFormatException) {
//            println("Ошибка: неверный формат года. Пожалуйста, введите число.")
//            return null
//        }
//
//        val book = BookLib(0, title, author, genre, isbn, year ) // Создаем объект книги
//        return book
//    }
//
//    suspend fun getAllBooks(): List<BookLib> {
//        val books = client.get<List<BookLib>>("http://localhost:8080/books")
//        return books
//    }
//
//    private suspend fun getBook(id: Int): BookLib {
//        val book = client.get<BookLib>("http://localhost:8080/books/$id")
//        return book
//    }
//
//    private suspend fun createBook(book: BookLib): Boolean {
//        val response = client.post<HttpResponse>("http://localhost:8080/books") {
//            contentType(ContentType.Application.Json)
//            setBody(book)
//        }
//        return response.status.value == 200
//    }
//
//    private suspend fun updateBook(id: Int, book: BookLib): Boolean {
//        val response = client.put<HttpResponse>("http://localhost:8080/books/$id") {
//            contentType(ContentType.Application.Json)
//            setBody(book)
//        }
//        return response.status.value == 200
//    }
//
//    private suspend fun deleteBook(id: Int): Boolean {
//        val response = client.delete<HttpResponse>("http://localhost:8080/books/$id")
//        return response.status.value == 200
//    }
//
//
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            val client = HttpClient(CIO) {
//                install(ContentNegotiation) {
//                    json()
//                }
//            }
//
//            val clientCLI = BookServiceClientCLI(client)
//            clientCLI.run()
//        }
//    }
//}
//
