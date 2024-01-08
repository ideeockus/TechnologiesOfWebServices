package org.lab1

class BookLib(
    var id: Int,
    var title: String?,
    var author: String?,
    var genre: String?,
    var isbn: String?,
    var year: Int,
) {

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
