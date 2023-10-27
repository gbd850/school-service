package dev.peter.springdatajpapractice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long id;
    private String title;
    private Integer credit;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "course_material_id",
            referencedColumnName = "id"
    )
    private CourseMaterial courseMaterial;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "id"
    )
    private Teacher teacher;
}
