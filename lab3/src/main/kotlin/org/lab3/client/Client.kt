package org.lab3.client

import org.lab3.BookLib
import org.lab3.BookService
import java.util.*

class BookServiceClientCLI(
    private val bookService: BookService
) {
    fun run() {
        val scanner = Scanner(System.`in`)

        while (true) {
            println("Выберите действие: c - Добавить книгу, r - Получить книгу, u - Обновить книгу, d - Удалить книгу, e - Выйти")
            val choice = scanner.nextLine()

            when (choice) {
                "c" -> {
                    // Логика добавления книги
                    val book = inputBook()
                    val success = bookService.createBook(book)
                    if (success) println("Книга добавлена успешно") else println("Ошибка при добавлении книги")
                }
                "r" -> {
                    // Логика получения книги
                    println("Введите ID книги:")
                    val id = scanner.nextLine().toInt()
                    val book = bookService.getBook(id)
                    println("Книга: $book")
                }
                "u" -> {
                    // Логика обновления книги
                    println("Введите ID книги для обновления:")
                    val id = scanner.nextLine().toInt()

                    val updatedBook = inputBook()
                    if (updatedBook != null) {
                        updatedBook.id = id
                    } else continue;

                    val success = bookService.updateBook(updatedBook)
                    if (success) println("Книга обновлена успешно") else println("Ошибка при обновлении книги")
                }
                "d" -> {
                    // Логика удаления книги
                    println("Введите ID книги для удаления:")
                    val id = scanner.nextLine().toInt()
                    val success = bookService.deleteBook(id)
                    if (success) println("Книга удалена успешно") else println("Ошибка при удалении книги")
                }
                "e" -> {
                    println("Выход из программы...")
                    return
                }
                else -> {
                    println("Некорректный ввод. Попробуйте снова.")
                }
            }

        }
    }

    fun inputBook(): BookLib? {
        val scanner = Scanner(System.`in`)

        // Запрашиваем данные для обновления
        println("Введите название книги:")
        val title = scanner.nextLine()
        println("Введите автора книги:")
        val author = scanner.nextLine()
        println("Введите жанр книги:")
        val genre = scanner.nextLine()
        println("Введите isbn книги:")
        val isbn = scanner.nextLine()
        println("Введите год издания книги:")
        val yearInput = scanner.nextLine()
        val year: Int;

        try {
            year = yearInput?.toInt() ?: throw NumberFormatException("Год не введен")
        } catch (e: NumberFormatException) {
            println("Ошибка: неверный формат года. Пожалуйста, введите число.")
            return null
        }

        val book = BookLib(0, title, author, genre, isbn, year ) // Создаем объект книги
        return book
    }

    companion object {
        // Методы для взаимодействия с bookService (добавление, получение, обновление, удаление книг)
        @JvmStatic
        fun main(args: Array<String>) {
            val bookService: BookService = BookService()

            val clientCLI = BookServiceClientCLI(bookService)
            clientCLI.run()
        }
    }
}
