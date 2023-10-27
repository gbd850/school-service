package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.TeacherRequestDto;
import dev.peter.springdatajpapractice.model.Teacher;
import dev.peter.springdatajpapractice.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {

    private TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher createTeacher(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = Teacher.builder()
                .firstName(teacherRequestDto.getFirstName())
                .lastName(teacherRequestDto.getLastName())
                .build();
        return teacherRepository.save(teacher);
    }
}
