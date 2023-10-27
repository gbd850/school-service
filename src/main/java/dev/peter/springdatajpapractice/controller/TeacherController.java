package dev.peter.springdatajpapractice.controller;

import dev.peter.springdatajpapractice.dto.TeacherRequestDto;
import dev.peter.springdatajpapractice.model.Teacher;
import dev.peter.springdatajpapractice.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teachers")
@AllArgsConstructor
public class TeacherController {

    private TeacherService teacherService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher createTeacher(@RequestBody TeacherRequestDto teacherRequestDto) {
        return teacherService.createTeacher(teacherRequestDto);
    }
}
