package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.CourseRequestDto;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.CourseMaterial;
import dev.peter.springdatajpapractice.model.Teacher;
import dev.peter.springdatajpapractice.repository.CourseMaterialRepository;
import dev.peter.springdatajpapractice.repository.CourseRepository;
import dev.peter.springdatajpapractice.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
    private CourseMaterialRepository courseMaterialRepository;
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(CourseRequestDto courseRequestDto) {
        if (courseRequestDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be null");
        }

        Course.CourseBuilder course = Course.builder()
                .title(courseRequestDto.getTitle())
                .credit(courseRequestDto.getCredit())
                .courseMaterial(null);
        if (courseRequestDto.getCourseMaterialUrl() != null && !courseRequestDto.getCourseMaterialUrl().isEmpty()) {
            String url = courseRequestDto.getCourseMaterialUrl();
            CourseMaterial courseMaterial = courseMaterialRepository.findByUrl(url)
                    .orElse(CourseMaterial.builder().url(url).build());
            course.courseMaterial(courseMaterial);
        }
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
