package dev.peter.springdatajpapractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String guardianName;
    private String guardianEmail;
    private String guardianMobile;
}
