package com.example.demo.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
	boolean existsByLevelIdAndUser_EmailAndPassedTrueAndPassedTrue(Long levelId, String email);
  List<Solution> findByUser_Email(String email);
}
