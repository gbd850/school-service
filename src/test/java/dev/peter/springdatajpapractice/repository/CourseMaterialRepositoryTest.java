package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.CourseMaterial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CourseMaterialRepositoryTest {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @AfterEach
    void tearDown() {
        courseMaterialRepository.deleteAll();
    }

    @Test
    void givenUrlExists_whenFindByUrl_thenReturnCourseMaterialOptional() {
        // given
        String url = "url";
        CourseMaterial courseMaterial = new CourseMaterial(1L, url);
        courseMaterialRepository.save(courseMaterial);
        // when
        Optional<CourseMaterial> expected = courseMaterialRepository.findByUrl(url);
        // then
        assertThat(expected).isNotEmpty()
                .get()
                .isEqualTo(courseMaterial);
    }

    @Test
    void givenUrlDoesNotExist_whenFindByUrl_thenReturnEmptyOptional() {
        // given
        String url = "url";
        // when
        Optional<CourseMaterial> expected = courseMaterialRepository.findByUrl(url);
        // then
        assertThat(expected).isEmpty();
    }
}