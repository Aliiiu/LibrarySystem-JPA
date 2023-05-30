package org.library.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.library.models.Student;

import java.util.List;

public class StudentController {
    private final EntityManager entityManager;
    private final EntityManagerFactory emf;
    private static final Logger logger = LogManager.getLogger(StudentController.class);

    public StudentController(String pu){
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = this.emf.createEntityManager();
    }

    public List<Student> getAllStudent(){
        Query query = entityManager.createQuery("SELECT s FROM Student s");
        return query.getResultList();
    }

    public Student addStudent(Student student){
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        logger.info("Student with name " + student.getName() + " added successfully");
        return student;
    }


    public Student findById(Long id){
        return entityManager.find(Student.class, id);
    }

    public List<Student> searchByName(String keyword) {
        Query query = entityManager.createQuery("Select s from Student s where s.name like '%" + keyword + "%'");
        return query.getResultList();
    }


    public Student updateStudent(Student student){
        Student studentToUpdate = findById(student.getId());
        entityManager.getTransaction().begin();
        studentToUpdate.setName(student.getName());
        studentToUpdate.setHasBorrowedBook(student.getHasBorrowedBook());
        entityManager.getTransaction().commit();
        return studentToUpdate;
    }

    public void deleteStudent(Student student){
        entityManager.getTransaction().begin();
        entityManager.remove(student);
        entityManager.getTransaction().commit();
    }

    public void close(){
        this.entityManager.close();
        this.emf.close();
    }
}
