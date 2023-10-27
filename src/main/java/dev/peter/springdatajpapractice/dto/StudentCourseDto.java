package dev.peter.springdatajpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseDto {
    private Long studentId;
    private Long courseId;
}
