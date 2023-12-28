package dev.peter.springdatajpapractice.service;

import dev.peter.springdatajpapractice.dto.TeacherRequestDto;
import dev.peter.springdatajpapractice.model.Teacher;
import dev.peter.springdatajpapractice.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {

    private TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher createTeacher(TeacherRequestDto teacherRequestDto) {
        if (teacherRequestDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher cannot be null");
        }
        if (teacherRequestDto.getFirstName() == null || teacherRequestDto.getFirstName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid first name");
        }
        if (teacherRequestDto.getLastName() == null || teacherRequestDto.getLastName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid last name");
        }

        Teacher teacher = Teacher.builder()
                .firstName(teacherRequestDto.getFirstName())
                .lastName(teacherRequestDto.getLastName())
                .build();
        return teacherRepository.save(teacher);
    }
}
