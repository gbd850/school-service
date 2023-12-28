package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.TeacherRequestDto;
import dev.peter.springdatajpapractice.model.Teacher;
import dev.peter.springdatajpapractice.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    private TeacherService teacherService;


    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherService(teacherRepository);
    }

    @Test
    void whenGetAllTeachers_thenCallFindAll() {
        // when
        teacherService.getAllTeachers();
        // then
        verify(teacherRepository).findAll();
    }

    @Test
    void givenTeacher_whenCreateTeacher_thenCallSave() {
        // given
        TeacherRequestDto teacher = new TeacherRequestDto("Jan", "Kowalski");
        // when
        teacherService.createTeacher(teacher);
        // then
        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);

        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher capturedTeacher = teacherArgumentCaptor.getValue();
        Teacher expected = Teacher.builder()
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .build();

        assertThat(expected).isEqualTo(capturedTeacher);
    }

    @Test
    void givenNoTeacher_whenCreateTeacher_thenThrowException() {
        // given
        TeacherRequestDto teacher = null;
        // when
        // then
        assertThatThrownBy(() -> teacherService.createTeacher(teacher))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Teacher cannot be null");
    }

    @Test
    void givenNoTeacherFirstName_whenCreateTeacher_thenThrowException() {
        // given
        TeacherRequestDto teacher = new TeacherRequestDto(null, "Kowalski");
        // when
        // then
        assertThatThrownBy(() -> teacherService.createTeacher(teacher))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid first name");
    }

    @Test
    void givenNoTeacherLastName_whenCreateTeacher_thenThrowException() {
        // given
        TeacherRequestDto teacher = new TeacherRequestDto("Jan", null);
        // when
        // then
        assertThatThrownBy(() -> teacherService.createTeacher(teacher))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid last name");
    }
}