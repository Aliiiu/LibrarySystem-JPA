package org.library;

import org.junit.jupiter.api.*;
import org.library.controllers.StudentController;
import org.library.models.Book;
import org.library.models.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {

    private static StudentController studentController;
    private static Student student;
    @BeforeEach
    void beforeEach() {
        studentController = new StudentController("library-pu-test");
        student = new Student("Aliu Salaudeen");

        student = studentController.addStudent(student);
    }

    @AfterEach
    void afterEach() {
        studentController.close();
    }

    @Test
    void addStudent() {

        assertNotNull(student.getId());
        assertEquals(1, (long) student.getId());
    }

    @Test
    void testGetAllStudent(){
        List<Student> returnList = studentController.getAllStudent();
        assertTrue(returnList.size() > 0);
    }

    @Test
    void findById() {

        student = studentController.findById(student.getId());

        assertNotNull(student);
        assertNotNull(student.getId());
        assertEquals("Aliu Salaudeen", student.getName());
    }

    @Test
    void testSearchByName(){

        List<Student> searchList = studentController.searchByName("Aliu");

        assertTrue(searchList.size() > 0);
    }

    @Test
    void testSearchByNameNoRecord(){

        List<Student> searchList = studentController.searchByName("bob");

        assertEquals(0, searchList.size());
    }

    @Test
    void updateStudent() {
        student.setName("Aliu Oluwaseun Salaudeen");
        student = studentController.updateStudent(student);

        assertNotNull(student);
        assertEquals("Aliu Oluwaseun Salaudeen", student.getName());
    }

    @Test
    void deleteStudent() {
        studentController.deleteStudent(student);

        student = studentController.findById(student.getId());

        assertNull(student);
    }
}