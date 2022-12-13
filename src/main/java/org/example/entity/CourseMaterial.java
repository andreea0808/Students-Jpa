package org.example.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "course")
public class CourseMaterial {

    @Id//primary key
    @SequenceGenerator(
            name = "course_material_sequence",
            sequenceName = "course_material_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_material_sequence"
    )
    private Long courseMaterialId;
    private String url;

    @OneToOne(
            cascade = CascadeType.ALL, //pasarea permisiunilor de la parinte la copil
            fetch = FetchType.LAZY,//sunt de doua tipuri: Eager si Lazy
            optional = false //atunci cand incerci sa salvezi course, courseMaterial este mandatory, nu este optional
    )
    @JoinColumn( //se aplica pentru coloana course_id care este foreign Key, declaram coloana care poate uni cle doua tabele
            name = "course_id",//numele coloanei din baza de date
            referencedColumnName = "courseId" //atributul la care se refera
    )
    private Course course; //courseMaterial nu poate exista fara un curs, trebuie sa aiba un curs ca sa ii putem adauga cursMaterial
}