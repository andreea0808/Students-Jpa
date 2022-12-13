package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
// nu vreau ca Guardian sa fie  entitate ci doar am nevoie de proprietatile acestuia pentru tabelul Student
@Embeddable //il notam incorporabil pentru a spune lui Spring cal il folosim in alta clasa si ca nu vom crea un tabel pentru aceasta clasa
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AttributeOverrides({//suprascriem coloanele din baza de date din name in guardian_name, email in guardian_email si mobile in guardian_mobile, si vom declara un obiect de tip guardian in StudentRepositoryTest
        //deoarece am suprascris atributele si atributele apartinaind obiectului guardian din clasa Student nu mai sunt recunoscute
        @AttributeOverride(
                name = "name",
                column = @Column(name = "guardian_name")
        ),
        @AttributeOverride(
                name = "email",
                column = @Column(name = "guardian_email")
        ),
        @AttributeOverride(
                name = "mobile",
                column = @Column(name = "guardian_mobile")
        )
})
public class Guardian {

    private String name;
    private String email;
    private String mobile;
}