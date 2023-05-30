package org.library;

import org.junit.jupiter.api.*;
import org.library.controllers.BookController;
import org.library.models.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    private static BookController bookController;
    private static Book book;
    private static Book book2;
    private static Book book3;
    @BeforeEach
    void beforeEach() {
        bookController = new BookController("library-pu-test");

        book = new Book("1234","Java Programming", "John Smith");
        book2 = new Book("23223", "Design Pattern", "Jason Duece");
        book3 = new Book("15463","Database Pattern", "John Smith");
    }

    @AfterEach
    void afterEach() {
        bookController.close();
    }

    @Test
    void testAddBook() {

        bookController.addBook(book);

        assertNotNull(book.getId());
        assertEquals(1, (long) book.getId());
    }

    @Test
    void testGetAllBooks(){
        bookController.addBook(book);

        List<Book> returnList = bookController.getAllBooks();
        assertTrue(returnList.size() > 0);
    }

    @Test
    void testAddAllBooks(){
        ArrayList<Book> bookList = new ArrayList<>(List.of(book, book2, book3));
        bookController.addAllBooks(bookList);

        List<Book> returnList = bookController.getAllBooks();
        assertEquals(3, returnList.size());
    }

    @Test
    void testFindById() {
        book = new Book("2345", "Python Programming", "Jane Doe");

        bookController.addBook(book);

        book = bookController.findById(book.getId());

        assertNotNull(book);
        assertNotNull(book.getId());
        assertEquals("2345", book.getISBN());
    }

    @Test
    void testSearchByTitle(){
        ArrayList<Book> bookList = new ArrayList<>(List.of(book, book2, book3));
        bookController.addAllBooks(bookList);

        List<Book> searchList = bookController.searchByTitle("P");

        assertTrue(searchList.size() > 0);
    }

    @Test
    void testSearchByTitleNoRecord(){
        ArrayList<Book> bookList = new ArrayList<>(List.of(book, book2, book3));
        bookController.addAllBooks(bookList);

        List<Book> searchList = bookController.searchByTitle("p");

        assertEquals(0, searchList.size());
    }

    @Test
    void testSearchByAuthor(){
        ArrayList<Book> bookList = new ArrayList<>(List.of(book, book2, book3));
        bookController.addAllBooks(bookList);

        List<Book> searchList = bookController.searchByAuthor("Jason");

        assertTrue(searchList.size() > 0);
    }

    @Test
    void testSearchByAuthorNoRecord(){
        ArrayList<Book> bookList = new ArrayList<>(List.of(book, book2, book3));
        bookController.addAllBooks(bookList);

        List<Book> searchList = bookController.searchByAuthor("aliu");

        assertEquals(0, searchList.size());
    }

    @Test
    void testSearchByISBN(){
        ArrayList<Book> bookList = new ArrayList<>(List.of(book, book2, book3));
        bookController.addAllBooks(bookList);

        List<Book> searchList = bookController.searchByISBN("15");

        assertTrue(searchList.size() > 0);
    }

    @Test
    void testSearchByISBNNoRecord(){
        ArrayList<Book> bookList = new ArrayList<>(List.of(book, book2, book3));
        bookController.addAllBooks(bookList);

        List<Book> searchList = bookController.searchByISBN("98");

        assertEquals(0, searchList.size());
    }

    @Test
    void testUpdateBook() {
        book = new Book("2345", "Python Programming", "Jane Doe");

        bookController.addBook(book);

        book.setAuthor("Aliu Oluwaseun Salaudeen");
        book = bookController.updateBook(book);

        assertNotNull(book);
        assertEquals("Aliu Oluwaseun Salaudeen", book.getAuthor());
    }


    @Test
    void testDeleteBook() {
        book = new Book("5432", "Python Programming", "Jane Doe" );

        book = bookController.addBook(book);

        bookController.deleteBook(book);

        book = bookController.findById(book.getId());

        assertNull(book);
    }
}