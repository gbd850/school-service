package dev.peter.springdatajpapractice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "email_unique",
                columnNames = "email"
        )
)
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @Column(
            nullable = false
    )
    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "This is not an email")
    private String email;
//    @Embedded
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guardian_id",
            referencedColumnName = "id")
    private Guardian guardian;
    @ManyToMany
    /*!!!*/@JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Course> courses;
}
