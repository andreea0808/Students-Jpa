package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id//primary key
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long courseId;
    private String title;
    private Integer credit;

    @OneToOne( //pentru fiecare curs exista un courseMaterial
            //relatia oneToOne este bidirectonala asa ca se va declara atat in clasa Course cat si in clasa CourseMaterial
            //insa nu se va mai declara @JoinigCoumn pentru ca a fost deja declarata in CourseMaterial
            mappedBy = "course" //variabila de tip refetinta declarata in CourseMaterial:  private Course course, maparea one to one a fost deja defonita de catre atributul course din clasa CourseMaterial
    )
    private CourseMaterial courseMaterial;

    @ManyToOne( //am comentat relatia @OneToMany in clasa Teacher si am declarat relatia @ManyToOne in clasa Course deoarece relatiile ManyToOne sunt recomandate in defavoarea relatiilor OneToMany
            cascade = CascadeType.ALL//atunci cand salvezi un nou curs cu un nou profesor si un nou material de curs, toate vor fi salvate simultan(sau sterse, in functie de preferinta)
    )
    @JoinColumn(
            name = "teacher_id", //numele coloanei din baza de date
            referencedColumnName = "teacherId" //numele atributului din clasa Teacher
    )
    private Teacher teacher;

    //mai multi studenti pot avea multiple course_id
    //se va crea un al treilea tabel care reprezinta care va salva relatia dintre entitatile de tip student_id si course_id
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(//pentru crearea celui de-al treilea tabel, vom folosi @JoinTable
            name = "studen_course_map", //numele tabelului din baza de date care va fi creat
            joinColumns = @JoinColumn( //pnetru student, avem urmatoarele cursuri
                    name = "course_id",//numele coloanei din tabelul course din baza de date
                    referencedColumnName = "courseId" //numele de referinta din clasa Course, proprietatea din clasa
            ),
            inverseJoinColumns = @JoinColumn( //inversul celei de mai sus, pentru cursuri, avem studentii
                    name = "student_id",
                    referencedColumnName = "studentId"
            )
    )
    private List<Student> students; //un curs poate fi optat de mai mult studenti, deci avem nevoie de o lista de studenti care au optat pentru acelasi curs

    public void addStudents(Student student){
        if(students == null) students = new ArrayList<>();
        students.add(student);
    }
}
