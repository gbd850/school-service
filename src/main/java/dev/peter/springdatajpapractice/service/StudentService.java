package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.StudentCourseDto;
import dev.peter.springdatajpapractice.dto.StudentRequestDto;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.Guardian;
import dev.peter.springdatajpapractice.model.Student;
import dev.peter.springdatajpapractice.repository.CourseRepository;
import dev.peter.springdatajpapractice.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(StudentRequestDto studentDto) {
        Student student = Student.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .email(studentDto.getEmail())
                .guardian(Guardian.builder()
                        .name("Guardian1")
                        .email("guardian1@gmail.com")
                        .mobile("123456789")
                        .build())
                .build();
        return studentRepository.save(student);
    }

    public ResponseEntity<Student> addCourseToStudent(StudentCourseDto studentCourseDto) {
        Optional<Student> student = studentRepository.findById(studentCourseDto.getStudentId());
        Optional<Course> course = courseRepository.findById(studentCourseDto.getCourseId());
        if (student.isEmpty() || course.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        student.get().getCourses().add(course.get());
        return new ResponseEntity<>(studentRepository.save(student.get()), HttpStatus.OK);
    }
}
