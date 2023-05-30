package org.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.library.controllers.BookController;
import org.library.controllers.BorrowedBookController;
import org.library.controllers.StudentController;
import org.library.models.Book;
import org.library.models.Student;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static BookController bookController;
    private static StudentController studentController;
    private static BorrowedBookController borrowedBookController;
    private static Book book1;
    private static Book book2;
    private static Book book3;

    public static void loader(){
        logger.info("\nInitializing...........................");

        borrowedBookController = new BorrowedBookController("library-pu");
        studentController = new StudentController("library-pu");
        bookController = new BookController("library-pu");

        //Create Dummy Books
        book1 = new Book("10505" , "Java Programming", "Alice Due");
        book2 = new Book("23223", "Design Pattern", "Jason Duece");
        book3 = new Book("15463","Database Pattern", "John Smith");

        ArrayList<Book> bookList = new ArrayList<>(List.of(book1, book2, book3));
        bookController.addAllBooks(bookList);
    }

    public static void main(String[] args) {
        loader();

        Student student1 = new Student("Alice");
        Student student2 = new Student("Bob");

        // Add students
        studentController.addStudent(student1);
        studentController.addStudent(student2);

        //  borrowBook(Student student, Book book, StudentController studentController)
        borrowedBookController.borrowBook(student1, book1, studentController);

        // Modeling borrowing another book without returning the previous one
        borrowedBookController.borrowBook(student1, book2, studentController);

        // returning book
        borrowedBookController.returnBook(student1, book1, studentController);

        // Get All students
        List<Student> studentList = studentController.getAllStudent();
        if (studentList.size() > 0){
            for (Student student : studentList){
                System.out.println(student);
            }
        } else {
            System.out.println("No student added record yet");
        }

        //searchByName(keyword: k)
        List<Student> filteredStudentList = studentController.searchByName("A");
        if (filteredStudentList.size() > 0){
            for (Student student : filteredStudentList){
                System.out.println(student);
            }
        } else {
            System.out.println("No match in record");
        }

        // Search by title
        List<Book> searchList = bookController.searchByTitle("P");
        if (searchList.size() > 0){
            for (Book book : searchList){
                System.out.println(book);
            }
        }else {
            System.out.println("No match in record");
        }

        // Search by author
        List<Book> searchList1 = bookController.searchByAuthor("J");
        if (searchList1.size() > 0){
            for (Book book : searchList1){
                System.out.println(book);
            }
        }else {
            System.out.println("No match in record");
        }

        // Search by ISBN
        List<Book> searchList2 = bookController.searchByISBN("154");
        if (searchList2.size() > 0){
            for (Book book : searchList2){
                System.out.println(book);
            }
        }else {
            System.out.println("No match in record");
        }

        // Get All books
        List<Book> bookList = bookController.getAllBooks();
        for (Book book : bookList){
            System.out.println(book);
        }

        borrowedBookController.close();
        studentController.close();
        bookController.close();
    }
}