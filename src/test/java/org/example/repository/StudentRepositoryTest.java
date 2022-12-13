package org.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.Guardian;
import org.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@SpringBootTest
//@SpringJpaTest - va testa layerul repository si dupa ce testele au fort completate, va sterge datele inserate in baza de date,
// a. i. baza de date sa nu fie impactata, insa noi dormi ca baza de date sa fie impactata, dorim sa stocam valori in baza de date
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository; //vom declara o referinta de tipul StudentRepository

    @Test
    public void saveStudent() {
        //pentru a salva un obiect de tip Student in baza de date, trebuie sa cream obiectul cu toate valorile existente in clasa Student
        Student student = Student.builder() //vom folosi patternul builder, nu vom declara studentId deoarece acesta va fi generat automat
                //cu ajutorul sequenceGenerater-ului
                .emailId("shabbir@gmail.com")
                .firstName("Shabbir")
                .lastName("Dawoodi")
                //.guardianName("Nikhil")
                //.guardianEmail("nikhil@gmail.com")
                //.guardianMobile("9999999999")
                .build();
        studentRepository.save(student);
    }

    @Test
    public void saveStudentWithGuardian() {
        Guardian guardian = Guardian.builder()
                .email("nikhil@gmail.com")
                .name("Nikhil")
                .mobile("9999956324")
                .build();

        Student student = Student.builder()
                .firstName("Shivam")
                .emailId("shivam@gmail.com")
                .lastName("Kumar")
                .guardian(guardian)
                .build();
        studentRepository.save(student);
    }

    @Test
    public void printAllStudent() {
        List<Student> studentList =
                studentRepository.findAll();
        System.out.println("studentList = " + studentList);
    }

    @Test
    public void printStudentByFirstName() {
        List<Student> students =
                studentRepository.findByFirstName("shivam");
        System.out.println("students = " + students);
    }

    @Test
    public void printStudentByFirstNameContaining() {
        List<Student> students =
                studentRepository.findByFirstNameContaining("sh");
        System.out.println("students = " + students);
    }

    @Test
    public void printStudentBasedOnGuardianName(){
        List<Student> students =
                studentRepository.findByGuardianName("Nikhil");
        System.out.println("students = " + students);
    }

    @Test
    public void printgetStudentByEmailAddress() {
        Student student =
                studentRepository.getStudentByEmailAddress(
                        "shabbir@gmail.com"
                );
        System.out.println("student = " + student);
    }

    @Test
    public void printgetStudentFirstNameByEmailAddress() {
        String firstName =
                studentRepository.getStudentFirstNameByEmailAddress(
                        "shivam@gmail.com"
                );
        System.out.println("firstName = " + firstName);
    }

    @Test
    public void printgetStudentByEmailAddressNative(){
        Student student =
                studentRepository.getStudentByEmailAddressNative(
                        "shivam@gmail.com"
                );
        System.out.println("student = " + student);
    }

    @Test
    public void printgetStudentByEmailAddressNativeNamedParam() {
        Student student =
                studentRepository.getStudentByEmailAddressNativeNamedParam(
                        "shivam@gmail.com"
                );

        System.out.println("student = " + student);
    }

    @Test
    public void updateStudentNameByEmailIdTest() {
        studentRepository.updateStudentNameByEmailId(
                "shabbir dawoodi",
                "shabbir@gmail.com");
    }
}