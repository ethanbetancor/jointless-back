package com.example.demo.data;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Level;

public interface LevelRepository extends JpaRepository<Level,Long>{
	List<Level> findByCategory(String category);
}
