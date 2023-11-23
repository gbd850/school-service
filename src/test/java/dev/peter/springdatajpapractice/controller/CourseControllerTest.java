package dev.peter.springdatajpapractice.controller;

import dev.peter.springdatajpapractice.SpringDataJpaPractiseApplication;
import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.service.CourseService;
import dev.peter.springdatajpapractice.service.StudentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringDataJpaPractiseApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class CourseControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CourseService courseService;

    @Test
    public void givenCourdeId_whenCourse_thenStatus200() throws Exception {

        Long id = 1L;
        when(courseService.getCourseById(id)).thenReturn(Course.builder().id(1L).title("Course 1").credit(5).build());

        mvc.perform(get("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("Course 1")).hasJsonPath());
    }
}