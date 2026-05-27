package com.example.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.entities.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
