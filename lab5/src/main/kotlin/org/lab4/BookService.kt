//package org.lab4
//
//import java.sql.SQLException
//
//
//@WebService(serviceName = "BookService")
//class BookService {
//    var soapFaultBuilder: SOAPFaultBuilder? = null
//
//    @WebMethod(operationName = "getAll")
//    open fun getAll(): List<BookLib> {
//        val dao = BookDAO()
//        return dao.listAllBooks()
//    }
//
//    @WebMethod(operationName = "createBook")
//    open fun createBook(book: BookLib?): Boolean {
//        val bookDAO = BookDAO()
//        try {
//            return bookDAO.insertBook(book)
//        } catch (e: SQLException) {
//            e.printStackTrace()
//            return false
//        }
//    }
//
//    @WebMethod(operationName = "getBook")
//    open fun getBook(id: Int): BookLib? {
//        val bookDAO = BookDAO()
//        try {
//            return bookDAO.getBook(id)
//        } catch (e: SQLException) {
//            e.printStackTrace()
//            return null
//        }
//    }
//
//    @WebMethod(operationName = "updateBook")
//    open fun updateBook(book: BookLib?): Boolean {
//        val bookDAO = BookDAO()
//        try {
//            return bookDAO.updateBook(book!!)
//        } catch (e: SQLException) {
//            e.printStackTrace()
//            return false
//        }
//    }
//
//    @WebMethod(operationName = "deleteBook")
//    open fun deleteBook(id: Int): Boolean {
//        val bookDAO = BookDAO()
//        try {
//            return bookDAO.deleteBook(id)
//        } catch (e: SQLException) {
//            e.printStackTrace()
//            return false
//        }
//    }
//}