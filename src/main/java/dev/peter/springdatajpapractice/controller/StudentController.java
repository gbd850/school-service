package dev.peter.springdatajpapractice.controller;

import dev.peter.springdatajpapractice.dto.StudentRequestDto;
import dev.peter.springdatajpapractice.model.Student;
import dev.peter.springdatajpapractice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody StudentRequestDto studentDto) {
        return studentService.createStudent(studentDto);
    }
}
