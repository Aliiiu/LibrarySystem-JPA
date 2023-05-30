package org.library.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.library.models.Book;

import java.util.List;

public class BookController {
    private EntityManager entityManager;
    private EntityManagerFactory emf;
    private static final Logger logger = LogManager.getLogger(BookController.class);

    public BookController(String pu){
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = this.emf.createEntityManager();
    }


    public Book addBook(Book book){
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        return book;
    }

    public List<Book> getAllBooks(){
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }

    public void addAllBooks(List<Book> books){
        for(Book book : books){
            addBook(book);
        }
        String msg = String.format("%d books added successfully", books.size());
        logger.info(msg);
    }


    public Book findById(Long id){
        return entityManager.find(Book.class, id);
    }

    public List<Book> searchByTitle(String keyword) {
        Query query = entityManager.createQuery("Select b from Book b where b.title like '%" + keyword + "%'");
        return query.getResultList();
    }
    public List<Book> searchByAuthor(String keyword) {
        Query query = entityManager.createQuery("Select b from Book b where b.author like '%" + keyword + "%'");
        return query.getResultList();
    }
    public List<Book> searchByISBN(String keyword) {
        Query query = entityManager.createQuery("Select b from Book b where b.ISBN like '%" + keyword + "%'");
        return query.getResultList();
    }


    public Book updateBook(Book book){
        Book bookToUpdate = findById(book.getId());
        entityManager.getTransaction().begin();
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setISBN(book.getISBN());
        entityManager.getTransaction().commit();
        return bookToUpdate;
    }


    public void deleteBook(Book book){
        entityManager.getTransaction().begin();
        entityManager.remove(book);
        entityManager.getTransaction().commit();
    }

    public void close(){
        entityManager.close();
        emf.close();
    }

}
