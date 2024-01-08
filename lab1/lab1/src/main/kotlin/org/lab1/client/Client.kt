package org.lab1.client

import org.lab1.BookLib
import org.lab1.BookService
import java.net.MalformedURLException
import java.net.URL


object Client {
    @Throws(MalformedURLException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val url = URL("http://localhost:8083/lab1_j2ee_1_0_SNAPSHOT_war/ws/book?wsdl")
        val bookService: BookService = BookService()

        val books: List<BookLib> = bookService.getAll()
        println("All books")
        for (book in books) {
            println(
                ("Books {" + "id=" + book.id
                        ).toString() + ", title=" + book.title.toString() +
                        ", author=" + book.author +
                        ", genre=" + book.genre +
                        ", isbn=" + book.isbn +
                        ", year=" + book.year + '}'
            )
        }
        println("Total books: " + books.size)

        println("\nBooks older than 1900")
        for (book in books) {
            if (book.year < 1900) {
                System.out.println(
                    ("book {Id=" + book.id).toString() + ", title=" + book.title.toString() + ", author=" +
                            book.author + ", genre=" + book.genre + ", isbn=" + book.isbn +
                            ", year=" + book.year + "}"
                )
            }
        }

        println("\nHorror books")
        for (book in books) {
            if (book.genre == "horror") {
                System.out.println(
                    ("book {Id=" + book.id + ", title=" + book.title + ", author=" +
                            book.author + ", genre=" + book.genre + ", isbn=" + book.isbn +
                            ", year=" + book.year + "}"
                ))
            }
        }
    }
}
