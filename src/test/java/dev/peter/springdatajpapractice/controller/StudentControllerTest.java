package dev.peter.springdatajpapractice.controller;

import com.google.gson.Gson;
import dev.peter.springdatajpapractice.SpringDataJpaPractiseApplication;
import dev.peter.springdatajpapractice.dto.StudentRequestDto;
import dev.peter.springdatajpapractice.model.Guardian;
import dev.peter.springdatajpapractice.model.Student;
import dev.peter.springdatajpapractice.repository.StudentRepository;
import dev.peter.springdatajpapractice.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringDataJpaPractiseApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void givenStudent_whenGetStudents_thenStatus200() throws Exception {

        when(studentService.getAllStudents()).thenReturn(List.of(Student.builder().id(1L).firstName("bob").build()));

        mvc.perform(get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("bob")).hasJsonPath());
    }

    @Test
    public void givenStudent_whenPostStudent_thenStatus201() throws Exception {

        StudentRequestDto studentRequestDto = new StudentRequestDto("Jan",
                "Kowalski",
                "wp@wp.pl",
                "G1",
                "g1@g.com",
                "123456789");

        Gson gson = new Gson();

        mvc.perform(post("/api/students")
                        .content(gson.toJson(studentRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
  
}