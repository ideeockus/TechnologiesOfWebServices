package org.lab1

class BookLib {
    var id: Int = 0
    var title: String? = null
    var author: String? = null
    var genre: String? = null
    var isbn: String? = null
    var year: Int = 0

    // Конструктор по умолчанию
    constructor()

    // Конструктор с аргументами
    constructor(id: Int, title: String, author: String, genre: String, isbn: String, year: Int) {
        this.id = id
        this.title = title
        this.author = author
        this.genre = genre
        this.isbn = isbn
        this.year = year
    }

    override fun toString(): String {
        return ("BookService {" + "Id=" + id
                + "title=" + title +
                ", author=" + author +
                ", genre=" + genre +
                ", isbn=" + isbn +
                ", year=" + year +
                '}')
    }
}
