package org.example.repository;

import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//JpaRepository este o interfata care extinde PagingAndSortingRepository si QueryByExampleExecutor. JpaRepository foloseste Genericele de tip<T, ID>
//T este entitatea, tipul si ID reprezinta tipul entitatii pe care am definit-o
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByFirstName(String firstName);

    List<Student> findByFirstNameContaining(String name);

    List<Student> findByLastNameNotNull();

    List<Student> findByGuardianName(String guardianName);

    Student findByFirstNameAndLastName(String firstName,
                                       String lastName);

    //JPQL - queries bazate pe clasele si atributele din java si nu pe tabelele din baza de date
    @Query("select s from Student s where s.emailId = ?1") // ?1 reprezinta primul parametru
    Student getStudentByEmailAddress(String emailId);


    //JPQL
    @Query("select s.firstName from Student s where s.emailId = ?1")
    String getStudentFirstNameByEmailAddress(String emailId); //String deoarece returneaza firstName si nu un obiect Student

    //Native query - atunci cand ai un query complex care nu se poate defini cu JPQL
    @Query(
            value = "SELECT * FROM tbl_student s where s.email_address = ?1", //?1 reprezinta primul parametru
            nativeQuery = true //spunem ca este un query nativ
    )
    Student getStudentByEmailAddressNative(String emailId);


    //Native Named Param atunci cand sunt mai multi paramatri
    @Query(
            value = "SELECT * FROM tbl_student s where s.email_address = :emailId",
            nativeQuery = true
    )
    Student getStudentByEmailAddressNativeNamedParam(
            @Param("emailId") String emailId //se declara parametru dupa care se face selectia
    );

    @Modifying //pentru a anunta ca datele se vor modifica
    @Transactional //pentru ca se vor modifica date, trebuie sa existe o tranzactie, pentru ca datle sa fie modificate(update, delete) si modificarea sa fie salvata in baza de date
   //ideal toate tranzactiile ar trebui declarate is in Service Layer si poti apela multiple Repository Layers din Service Layer pentru a face intregul flow de tip tranzactional
   //adnotam metodele din Service Layer cu @Transactional, aplezi metodele din RepositoryLayer si atunci cand totul s-a completat cu succes, la sfarsit tranzactia  va avea loc, altfel tranzactia va face roll back(
   //the process of restoring a database or program to a previously defined state, typically to recover from an error.)
    @Query(
            value = "update tbl_student set first_name = ?1 where email_address = ?2",
            nativeQuery = true
    )
    int updateStudentNameByEmailId(String firstName, String emailId);

}