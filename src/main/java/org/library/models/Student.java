package org.library.models;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
@NamedQuery(name = "getAllStudent", query = "SELECT s FROM Student s")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name="borrowStatus", nullable = false)
    private Boolean isHasBorrowedBook;

    public Student(){}
    public Student(String name){
        this.name = name;
        this.isHasBorrowedBook = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasBorrowedBook() {
        return isHasBorrowedBook;
    }

    public void setHasBorrowedBook(Boolean hasBorrowedBook) {
        this.isHasBorrowedBook = hasBorrowedBook;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isHasBorrowedBook=" + isHasBorrowedBook +
                '}';
    }

}
