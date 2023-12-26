package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.Course;
import dev.peter.springdatajpapractice.model.CourseMaterial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    void givenCourse_whenFindCourseByTitleFirstPage_thenReturnPageWithCourse() {
        // given
        String title = "title";
        CourseMaterial courseMaterial = new CourseMaterial(1L, "url");
        Course course = new Course(1L, title, 4, courseMaterial, null);
        courseRepository.save(course);
        // when
        String partialTitle = "tit";
        Page<Course> expected = courseRepository.findByTitleContaining(
                partialTitle,
                PageRequest.of(0, 3, Sort.by("title").descending())
        );
        // then
        assertThat(expected).isNotEmpty()
                .allMatch(c -> c.getTitle().contains(partialTitle))
                .allMatch(c -> c.getTitle().equals(title));
    }

    @Test
    void givenInvalidPartialTitle_whenFindCourseByTitleFirstPage_thenReturnEmptyPage() {
        // given
        String title = "title";
        CourseMaterial courseMaterial = new CourseMaterial(1L, "url");
        Course course = new Course(1L, title, 4, courseMaterial, null);
        courseRepository.save(course);
        // when
        String partialTitle = "invalidTitle";
        Page<Course> expected = courseRepository.findByTitleContaining(
                partialTitle,
                PageRequest.of(0, 3, Sort.by("title").descending())
        );
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenNoCourse_whenFindCourseByTitleFirstPage_thenReturnEmptyPage() {
        // given
        String title = "title";
        // when
        String partialTitle = "tit";
        Page<Course> expected = courseRepository.findByTitleContaining(
                partialTitle,
                PageRequest.of(0, 3, Sort.by("title").descending())
        );
        // then
        assertThat(expected).isEmpty();
    }
}