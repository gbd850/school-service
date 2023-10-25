package dev.peter.springdatajpapractise.service;

import dev.peter.springdatajpapractise.dto.StudentDto;
import dev.peter.springdatajpapractise.model.Guardian;
import dev.peter.springdatajpapractise.model.Student;
import dev.peter.springdatajpapractise.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(StudentDto studentDto) {
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
}
