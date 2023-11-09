package dev.peter.springdatajpapractice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Embeddable
//@AttributeOverrides({
//        @AttributeOverride(
//                name = "name",
//                column = @Column(name = "guardian_name")
//        ),
//        @AttributeOverride(
//                name = "email",
//                column = @Column(name = "guardian_email")
//        ),
//        @AttributeOverride(
//                name = "mobile",
//                column = @Column(name = "guardian_mobile")
//        )
//}
//)
public class Guardian {
    @Id
    @SequenceGenerator(
            name = "guardian_sequence",
            sequenceName = "guardian_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guardian_sequence"
    )
    private Long id;
    @NotBlank(message = "Guardian name cannot be blank")
    private String name;
    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "This is not an email")
    private String email;
    @NotBlank(message = "Phone number cannot be blank")
    @Length(min = 9, max = 9, message = "Phone number must be 9 digits long")
    private String mobile;
}
