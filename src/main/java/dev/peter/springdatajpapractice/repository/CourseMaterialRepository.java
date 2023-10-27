package dev.peter.springdatajpapractice.repository;

import dev.peter.springdatajpapractice.model.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
    Optional<CourseMaterial> findByUrl(String url);
}
