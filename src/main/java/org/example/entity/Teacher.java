package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    @Id //primary key
    @SequenceGenerator(
            name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teacher_sequence"
    )
    private Long teacherId;
    private String firstName;
    private String lastName;

    //un teacher poate preda mai multe cursuri, si invers, mai multe cursuri pot fi predate de un Teacher: relatie de tip manyToOne
    //@ManyToMany - o lista de cursuri poate fi predata de un singur profesor asa ca in loc sa declaram *@OneToMany in clasa Teachr, vom declara o relatie @ManyToOne in clasa Course
    /*@OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "teacherId"
    )
    private List<Course> courses;*/ //profesorul poate avea o lista de cursuri pe care le poate preda
}