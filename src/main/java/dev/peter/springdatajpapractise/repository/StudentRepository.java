package dev.peter.springdatajpapractise.repository;

import dev.peter.springdatajpapractise.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFirstName(String firstName);
    List<Student> findByFirstNameContaining(String partialName);
    List<Student> findByGuardianNameNotNull();
}
