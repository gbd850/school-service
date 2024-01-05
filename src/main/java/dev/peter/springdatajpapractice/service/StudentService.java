package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.StudentCourseDto;
import dev.peter.springdatajpapractice.dto.StudentRequestDto;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.Guardian;
import dev.peter.springdatajpapractice.model.Student;
import dev.peter.springdatajpapractice.repository.CourseRepository;
import dev.peter.springdatajpapractice.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (studentDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student cannot be null");
        }

        boolean isValidGuardian =
                (studentDto.getGuardianName() != null && !studentDto.getGuardianName().isEmpty()) &&
                        (studentDto.getGuardianMobile() != null && !studentDto.getGuardianMobile().isEmpty()) &&
                        (studentDto.getGuardianEmail() != null && !studentDto.getGuardianEmail().isEmpty());

        Student student = Student.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .email(studentDto.getEmail())
                .guardian(isValidGuardian ? Guardian.builder()
                        .name(studentDto.getGuardianName())
                        .email(studentDto.getGuardianEmail())
                        .mobile(studentDto.getGuardianMobile())
                        .build()
                        : null)
                .build();
        return studentRepository.save(student);
    }

    public ResponseEntity<Student> addCourseToStudent(StudentCourseDto studentCourseDto) {
        if (studentCourseDto.getStudentId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student id cannot be null");
        }
         if (studentCourseDto.getCourseId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course id cannot be null");
        }

        Student student = studentRepository.findById(studentCourseDto.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid student id"));
        Course course = courseRepository.findById(studentCourseDto.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid course id"));
        student.getCourses().add(course);
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }
}
