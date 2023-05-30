package org.library.controllers;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.library.models.Book;
import org.library.models.BorrowedBook;
import org.library.models.Student;

public class BorrowedBookController {
    private EntityManager entityManager;
    private EntityManagerFactory emf;
    private static final Logger logger = LogManager.getLogger(BorrowedBookController.class);

    public BorrowedBookController(String pu){
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = this.emf.createEntityManager();
    }

    public String borrowBook(Student student, Book book, StudentController studentController){
        String msg;

        if (student.getId() == null){
            msg = "student details is not registered";
            logger.warn(msg);
            return msg;
        }
        if (book.getId() == null){
            msg = "book is not available";
            logger.warn(msg);
            return msg;
        }

        entityManager.getTransaction().begin();

        // Check if the student has any borrowed books
        if (student.getHasBorrowedBook()) {
            msg = "You have already borrowed a book. Please return the previous book before borrowing another.";
            logger.warn(msg);
        } else {
            student.setHasBorrowedBook(true);
            studentController.updateStudent(student);

            BorrowedBook borrowedBook = new BorrowedBook(student, book);

            entityManager.persist(borrowedBook);
            msg = "Book borrowed successfully.";
            logger.info(msg);
        }

        entityManager.getTransaction().commit();
        return msg;

    }

    public String returnBook(Student student, Book book, StudentController studentController){
        String msg;

        if (student.getId() == null){
            msg = "student details is not registered";
            logger.warn(msg);
            return msg;
        }

        if (book.getId() == null){
            msg = "book is not available";
            logger.warn(msg);
            return msg;
        }
        if(!student.getHasBorrowedBook()){
            msg = "You don't have any borrowed book to return.";
            System.out.println(msg);
            logger.warn(msg);
            return msg;
        }


//        Query query = entityManager.createQuery("Select b from BorrowedBook b where b.book like '%" + book.getId() + "%'");
//
//        if (query.getResultList().size() == 0){
//            msg = "Returning wrong book";
//            System.out.println(msg);
//            logger.warn(msg);
//            return msg;
//        }
        entityManager.getTransaction().begin();

        try{
            BorrowedBook borrowedBook = entityManager.createQuery("SELECT b FROM BorrowedBook b WHERE b.student = :student AND b.book = :book", BorrowedBook.class)
                    .setParameter("student", student)
                    .setParameter("book", book)
                    .getSingleResult();
            student.setHasBorrowedBook(false);
            studentController.updateStudent(student);

            entityManager.remove(borrowedBook);
            msg = book.getTitle() + " returned successfully by " + student.getName();
            System.out.println(msg);
            logger.info(msg);

            entityManager.getTransaction().commit();
            return msg;

        } catch (NoResultException e){
            msg = student.getName() + " is returning the wrong book: " + book.getTitle();
            System.out.println(msg);
            logger.warn(msg);
            logger.error(e.getMessage());
            return msg;
        }
    }

    public void close(){
        this.entityManager.close();
        this.emf.close();
    }
}
