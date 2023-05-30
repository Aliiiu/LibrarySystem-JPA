package org.library;

import org.junit.jupiter.api.*;
import org.library.controllers.BookController;
import org.library.controllers.BorrowedBookController;
import org.library.controllers.StudentController;
import org.library.models.Book;
import org.library.models.BorrowedBook;
import org.library.models.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BorrowedBookControllerTest {

    private static BorrowedBookController borrowedBookController;
    private static StudentController studentController;
    private static BookController bookController;
    private static Book book1;
    private static Book book2;
    private static Book book3;
    @BeforeEach
    void beforeEach() {

        borrowedBookController = new BorrowedBookController("library-pu-test");
        studentController = new StudentController("library-pu-test");
        bookController = new BookController("library-pu-test");

        //Create Dummy Books
        book1 = new Book("10505" , "Java Programming", "Alice Due");
        book2 = new Book("23223", "Design Pattern", "Jason Duece");
        book3 = new Book("15463","Database Pattern", "John Smith");

        ArrayList<Book> bookList = new ArrayList<>(List.of(book1, book2, book3));
        bookController.addAllBooks(bookList);
    }

    @AfterEach
    void afterEach() {
        borrowedBookController.close();
        studentController.close();
        bookController.close();
    }

    @Test
    void testBorrowBook() {

        // Add students to the library
        Student student1 = new Student("Alice");
        studentController.addStudent(student1);

        String res = borrowedBookController.borrowBook(student1, book1, studentController);
        assertEquals("Book borrowed successfully.", res);
    }

    @Test
    void testNotRegisteredStudentBorrowBook() {

        // Add students to the library
        Student student1 = new Student("Alice");

        String res = borrowedBookController.borrowBook(student1, book1, studentController);
        assertEquals("student details is not registered", res);
    }

    @Test
    void testNotAddedBookBorrowBook() {

        // Add students to the library
        Student student1 = new Student("Alice");
        studentController.addStudent(student1);
        Book book4 = new Book("15472","Database Design", "John Smith");
        String res = borrowedBookController.borrowBook(student1, book4, studentController);
        assertEquals("book is not available", res);
    }

    @Test
    void testStudentHasBorrowedBook(){
        String res;
        // Add students to the library
        Student student1 = new Student("Alice");
        studentController.addStudent(student1);

        borrowedBookController.borrowBook(student1, book1, studentController);
        res = borrowedBookController.borrowBook(student1, book2, studentController);
        assertEquals("You have already borrowed a book. Please return the previous book before borrowing another.", res);

    }


    @Test
    void testReturnBook() {

        // Add students to the library
        Student student1 = new Student("Alice");
        studentController.addStudent(student1);

        borrowedBookController.borrowBook(student1, book1, studentController);

        String res = borrowedBookController.returnBook(student1, book1, studentController);
        assertEquals(book1.getTitle() + " returned successfully by " + student1.getName(), res);
    }

    @Test
    void testNotRegisteredStudentReturnBook() {

        // Add students to the library
        Student student1 = new Student("Alice");

        borrowedBookController.borrowBook(student1, book1, studentController);

        String res = borrowedBookController.returnBook(student1, book1, studentController);
        assertEquals("student details is not registered", res);
    }

    @Test
    void testNotAddedBookReturnBook() {

        // Add students to the library
        Student student1 = new Student("Alice");
        studentController.addStudent(student1);

        Book book4 = new Book("15472","Database Design", "John Smith");
        String res = borrowedBookController.returnBook(student1, book4, studentController);
        assertEquals("book is not available", res);
    }

    @Test
    void testDidNotBorrowBookReturnBook() {

        // Add students to the library
        Student student1 = new Student("Alice");
        studentController.addStudent(student1);

        String res = borrowedBookController.returnBook(student1, book1, studentController);
        assertEquals("You don't have any borrowed book to return.", res);
    }

    @Test
    void testInvalidReturnRequest() {
        String res;
        // Add students to the library
        Student student1 = new Student("Alice");
        studentController.addStudent(student1);
        borrowedBookController.borrowBook(student1, book1, studentController);

        res = borrowedBookController.returnBook(student1, book2, studentController);
        assertEquals(student1.getName() + " is returning the wrong book: " + book2.getTitle(), res);
    }

}