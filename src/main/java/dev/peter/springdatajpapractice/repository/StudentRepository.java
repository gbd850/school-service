package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFirstName(String firstName);
    List<Student> findByFirstNameContaining(String partialName);
    List<Student> findByGuardianNameNotNull();
    @Query("select s from Student s where s.email = ?1")
    //JPQL query
    Optional<Student> getStudentByEmail(String email);
    @Query(
            value = "SELECT * FROM student s WHERE s.email = :email",
            nativeQuery = true
    )
    //Native SQL query with named parameters
    Optional<Student> getStudentByEmailNativeNamedParam(@Param("email") String email);
}
