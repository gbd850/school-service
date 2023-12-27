package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.CourseRequestDto;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.CourseMaterial;
import dev.peter.springdatajpapractice.repository.CourseMaterialRepository;
import dev.peter.springdatajpapractice.repository.CourseRepository;
import dev.peter.springdatajpapractice.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private CourseMaterialRepository courseMaterialRepository;

    @BeforeEach
    void setUp() {
        courseService = new CourseService(courseRepository, teacherRepository, courseMaterialRepository);
    }

    @Test
    void whenGetAllCourses_thenCallFindAll() {
        // when
        courseService.getAllCourses();
        // then
        verify(courseRepository).findAll();
    }

    @Test
    void givenCourse_whenCreateCourse_thenCallSave() {
        // given
        CourseRequestDto course = new CourseRequestDto("title", 5, null, null);
        // when
        courseService.createCourse(course);
        // then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);

        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();

        Course expected = Course.builder()
                .title(course.getTitle())
                .credit(course.getCredit())
                .courseMaterial(null)
                .teacher(null)
                .build();

        assertThat(capturedCourse).isEqualTo(expected);
    }

    @Test
    void givenNoCourse_whenCreateCourse_thenThrowException() {
        // given
        CourseRequestDto course = null;
        // when
        // then
        assertThatThrownBy(() -> courseService.createCourse(course))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Course cannot be null");

        verify(courseRepository, never()).save(any());
    }

    @Test
    void givenValidCourseId_whenGetCourseById_thenCallFindById() {
        // given
        Long id = 1L;
        given(courseRepository.findById(anyLong()))
                .willReturn(Optional.of(Course.builder().build()));
        // when
        courseService.getCourseById(id);
        // then
        verify(courseRepository).findById(id);

    }

    @Test
    void givenInvalidCourseId_whenGetCourseById_thenThrowException() {
        // given
        Long id = 1L;
        // when
        // then
        assertThatThrownBy(() -> courseService.getCourseById(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Course not found");

    }
}