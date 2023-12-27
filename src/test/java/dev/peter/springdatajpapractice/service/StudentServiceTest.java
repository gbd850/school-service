package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.StudentCourseDto;
import dev.peter.springdatajpapractice.dto.StudentRequestDto;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.Student;
import dev.peter.springdatajpapractice.repository.CourseRepository;
import dev.peter.springdatajpapractice.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository, courseRepository);
    }

    @Test
    void whenGetAllStudents_thenCallFindAll() {
        // when
        studentService.getAllStudents();
        // then
        verify(studentRepository).findAll();
    }

    @Test
    void givenStudent_whenCreateStudent_thenCallSave() {
        // given
        StudentRequestDto student = new StudentRequestDto(
                "Jan",
                "Kowalski",
                "example@mail.com",
                null,
                null,
                null
        );
        // when
        studentService.createStudent(student);
        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        Student expected = Student.builder()
                .firstName(capturedStudent.getFirstName())
                .lastName(capturedStudent.getLastName())
                .email(capturedStudent.getEmail())
                .guardian(null)
                .build();

        assertThat(expected).isEqualTo(capturedStudent);
    }

    @Test
    void givenNoStudent_whenCreateStudent_thenThrowException() {
        // given
        StudentRequestDto student = null;
        // when
        // then
        assertThatThrownBy(() -> studentService.createStudent(student))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Student cannot be null");
    }

    @Test
    void givenValidStudentIdAndCourseId_whenAddCourseToStudent_thenCallSave() {
        // given
        Long studentId = 1L;
        Long courseId = 1L;
        Course sampleCourse = Course.builder().build();
        given(studentRepository.findById(anyLong())).willReturn(Optional.of(Student.builder().courses(new HashSet<>()).build()));
        given(courseRepository.findById(anyLong())).willReturn(Optional.of(sampleCourse));

        StudentCourseDto studentCourseDto = new StudentCourseDto(studentId, courseId);
        // when
        studentService.addCourseToStudent(studentCourseDto);
        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        Set<Course> studentCourses = capturedStudent.getCourses();

        assertThat(studentCourses).hasSize(1)
                .allMatch(c -> c.equals(sampleCourse));
    }

    @Test
    void givenInvalidStudentId_whenAddCourseToStudent_thenThrowException() {
        // given
        Long studentId = 1L;
        Long courseId = 1L;

        StudentCourseDto studentCourseDto = new StudentCourseDto(studentId, courseId);
        // when
        // then
        assertThatThrownBy(() -> studentService.addCourseToStudent(studentCourseDto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid student id");
    }

    @Test
    void givenInvalidCourseId_whenAddCourseToStudent_thenThrowException() {
        // given
        Long studentId = 1L;
        Long courseId = 1L;
        given(studentRepository.findById(anyLong())).willReturn(Optional.of(Student.builder().courses(new HashSet<>()).build()));

        StudentCourseDto studentCourseDto = new StudentCourseDto(studentId, courseId);
        // when
        // then
        assertThatThrownBy(() -> studentService.addCourseToStudent(studentCourseDto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid course id");
    }

    @Test
    void givenNoStudentId_whenAddCourseToStudent_thenThrowException() {
        // given
        Long studentId = null;
        Long courseId = 1L;

        StudentCourseDto studentCourseDto = new StudentCourseDto(studentId, courseId);
        // when
        // then
        assertThatThrownBy(() -> studentService.addCourseToStudent(studentCourseDto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Student id cannot be null");
    }

    @Test
    void givenNoCourseId_whenAddCourseToStudent_thenThrowException() {
        // given
        Long studentId = 1L;
        Long courseId = null;

        StudentCourseDto studentCourseDto = new StudentCourseDto(studentId, courseId);
        // when
        // then
        assertThatThrownBy(() -> studentService.addCourseToStudent(studentCourseDto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Course id cannot be null");
    }
}