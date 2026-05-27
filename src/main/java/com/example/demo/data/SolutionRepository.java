package com.example.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
	boolean existsByLevelIdAndUserIdAndPassedTrue(Long levelId, Long userId);
}
