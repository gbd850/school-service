package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
