package dev.peter.springdatajpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequestDto {
    private String firstName;
    private String lastName;
}
