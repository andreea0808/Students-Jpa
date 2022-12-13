package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // pentru a reprezenta clasa in baza de date, pentru a mapa clasa la tabelul din baza de date, orice camp vom adauga in aceasta clasa va fi reflectat in baza de date
@Data // lomboc va genara getters, stetters, toString
@AllArgsConstructor
@NoArgsConstructor
@Builder //adaugam builder pattern
@Table( // pentru a schimba numele tabelului din baza de date, din student in tb-student, insa vechiul tabel student va ramane in baza de date
        name = "tbl_student",
        uniqueConstraints = @UniqueConstraint(//vrem ca emailId sa fie unic pentru fiecare studen
                name = "emailid_unique", //denumim constrictorul ca email_unique
                columnNames = "email_address" //numele coloanei
        )
)
public class Student {

    @Id //definim campul studentId ca primaryKey, ce este un identificator unic pentru toate inregistrarile din tabel
    //tunci cand se lucreaza cu primary keys, acestea trebuie incremetate si stocate
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue( //cum se vor genera valorile pentru studentId
            strategy = GenerationType.SEQUENCE, //deoarece folosim sequence pt generarea valorilor
            generator = "student_sequence"
    )
    private Long studentId;
    private String firstName;
    private String lastName;

    @Column( //redefinim coloana din email_id in email_address, asa ca in baza de date va aparea email_address, ideal ar fi ca toate campurile clasei sa fie redefinite in acest mod
            name = "email_address",
            nullable = false //vrem ca emailId sa fie mereu prezent
    )
    private String emailId;

    @Embedded //notam clasa Guardian ca incorporat in clasa Student
    private Guardian guardian;
}