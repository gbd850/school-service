package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.CourseRequestDto;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.CourseMaterial;
import dev.peter.springdatajpapractice.repository.CourseMaterialRepository;
import dev.peter.springdatajpapractice.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;
    private CourseMaterialRepository courseMaterialRepository;
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(CourseRequestDto courseRequestDto) {
//        Course.CourseBuilder course = Course.builder()
//                .title(courseRequestDto.getTitle())
//                .credit(courseRequestDto.getCredit());
//        Optional<CourseMaterial> courseMaterialOptional = courseMaterialRepository.findByUrl(courseRequestDto.getCourseMaterialUrl());
//        courseMaterialOptional.ifPresentOrElse(
//                course::courseMaterial,
//                () -> course.courseMaterial(
//                        CourseMaterial.builder().url(courseRequestDto.getCourseMaterialUrl()).build()
//                )
//        );
//        return courseRepository.save(course.build());
        Course course = Course.builder()
                .title(courseRequestDto.getTitle())
                .credit(courseRequestDto.getCredit())
                .courseMaterial(CourseMaterial.builder()
                        .url(courseRequestDto.getCourseMaterialUrl())
                        .build())
                .build();
        return courseRepository.save(course);
    }
}
