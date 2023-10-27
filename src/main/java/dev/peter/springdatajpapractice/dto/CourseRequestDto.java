package dev.peter.springdatajpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    private String title;
    private Integer credit;
    private String courseMaterialUrl;
}
