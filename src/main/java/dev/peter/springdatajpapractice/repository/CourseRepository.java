package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByTitleContaining(String title, Pageable pageable);
//    Example:
//    PageRequest page = PageRequest.of(0, 3, Sort.by("title").descending());
//    findByTitleContaining("partialTitle", page).getContent();
}
