package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void givenStudent_whenFindByFirstName_thenReturnStudentsList() {
        // given
        String firstName = "Jan";
        Student student = new Student(1L, firstName, "Kowalski", "example@mail.com", null, new HashSet<>());
        studentRepository.save(student);
        // when
        List<Student> expected = studentRepository.findByFirstName(firstName);
        // then
        assertThat(expected).isNotEmpty().allMatch(s -> s.getFirstName().equals(firstName));
    }

    @Test
    void givenNoStudent_whenFindByFirstName_thenReturnEmptyList() {
        // given
        String firstName = "Jan";
        // when
        List<Student> expected = studentRepository.findByFirstName(firstName);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenInvalidFirstName_whenFindByFirstName_thenReturnEmptyList() {
        // given
        Student student = new Student(1L, "Jan", "Kowalski", "example@mail.com", null, new HashSet<>());
        studentRepository.save(student);
        // when
        String invalidFirstName = "Marek";
        List<Student> expected = studentRepository.findByFirstName(invalidFirstName);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenStudent_whenFindByPartialFirstName_thenReturnStudentsList() {
        // given
        String firstName = "Marek";
        Student student = new Student(1L, firstName, "Kowalski", "example@mail.com", null, new HashSet<>());
        studentRepository.save(student);
        // when
        String partialFirstName = "Ma";
        List<Student> expected = studentRepository.findByFirstNameContaining(partialFirstName);
        // then
        assertThat(expected).isNotEmpty()
                .allMatch(s -> s.getFirstName().contains(partialFirstName))
                .allMatch(s -> s.getFirstName().equals(firstName));
    }

    @Test
    void givenNoStudent_whenFindByPartialFirstName_thenReturnEmptyList() {
        // given
        String firstName = "Marek";
        // when
        String partialFirstName = "Ma";
        List<Student> expected = studentRepository.findByFirstNameContaining(partialFirstName);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenInvalidPartialFirstName_whenFindByPartialFirstName_thenReturnEmptyList() {
        // given
        String firstName = "Marek";
        Student student = new Student(1L, firstName, "Kowalski", "example@mail.com", null, new HashSet<>());
        studentRepository.save(student);
        // when
        String partialFirstName = "Ja";
        List<Student> expected = studentRepository.findByFirstNameContaining(partialFirstName);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    @Disabled
    void findByGuardianNameNotNull() {
    }

    @Test
    void givenStudent_whenFindStudentByEmail_thenReturnStudentOptional() {
        // given
        String email = "example@mail.com";
        Student student = new Student(1L, "Jan", "Kowalski", email, null, new HashSet<>());
        studentRepository.save(student);
        // when
        Optional<Student> expected = studentRepository.getStudentByEmail(email);
        // then
        assertThat(expected).isNotEmpty()
                .get()
                .isEqualTo(student);
    }

    @Test
    void givenNoStudent_whenFindStudentByEmail_thenReturnEmptyOptional() {
        // given
        String email = "example@mail.com";
        // when
        Optional<Student> expected = studentRepository.getStudentByEmail(email);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenInvalidEmail_whenFindStudentByEmail_thenReturnEmptyOptional() {
        // given
        String email = "example@mail.com";
        Student student = new Student(1L, "Jan", "Kowalski", email, null, new HashSet<>());
        studentRepository.save(student);
        // when
        String invalidEmail = "invalid@mail.com";
        Optional<Student> expected = studentRepository.getStudentByEmail(invalidEmail);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenStudent_whenNativeSQLFindStudentByEmail_thenReturnStudentOptional() {
        // given
        String email = "example@mail.com";
        Student student = new Student(1L, "Jan", "Kowalski", email, null, new HashSet<>());
        studentRepository.save(student);
        // when
        Optional<Student> expected = studentRepository.getStudentByEmailNativeNamedParam(email);
        // then
        assertThat(expected).isNotEmpty()
                .get()
                .isEqualTo(student);
    }

    @Test
    void givenNoStudent_whenNativeSQLFindStudentByEmail_thenReturnEmptyOptional() {
        // given
        String email = "example@mail.com";
        // when
        Optional<Student> expected = studentRepository.getStudentByEmailNativeNamedParam(email);
        // then
        assertThat(expected).isEmpty();
    }

    @Test
    void givenInvalidEmail_whenNativeSQLFindStudentByEmail_thenReturnEmptyOptional() {
        // given
        String email = "example@mail.com";
        Student student = new Student(1L, "Jan", "Kowalski", email, null, new HashSet<>());
        studentRepository.save(student);
        // when
        String invalidEmail = "invalid@mail.com";
        Optional<Student> expected = studentRepository.getStudentByEmailNativeNamedParam(invalidEmail);
        // then
        assertThat(expected).isEmpty();
    }
}