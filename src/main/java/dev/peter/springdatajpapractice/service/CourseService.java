package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.CourseRequestDto;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.CourseMaterial;
import dev.peter.springdatajpapractice.model.Teacher;
import dev.peter.springdatajpapractice.repository.CourseRepository;
import dev.peter.springdatajpapractice.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;
    private TeacherRepository teacherRepository;
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(CourseRequestDto courseRequestDto) {
        Course.CourseBuilder course = Course.builder()
                .title(courseRequestDto.getTitle())
                .credit(courseRequestDto.getCredit())
                .courseMaterial(CourseMaterial.builder()
                        .url(courseRequestDto.getCourseMaterialUrl())
                        .build());
        if (courseRequestDto.getTeacher() != null) {
            Teacher teacher = courseRequestDto.getTeacher();
            Optional<Teacher> teacherOptional = teacherRepository.findById(teacher.getId());
            teacherOptional.ifPresentOrElse(
                    course::teacher,
                    () -> course.teacher(
                            Teacher.builder()
                                    .firstName(teacher.getFirstName())
                                    .lastName(teacher.getLastName())
                                    .build()
                    )
            );
        }
        return courseRepository.save(course.build());
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }
}
