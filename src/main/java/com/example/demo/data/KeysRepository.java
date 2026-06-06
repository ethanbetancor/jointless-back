package com.example.demo.data;

import com.example.demo.domain.entities.Keys;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KeysRepository extends JpaRepository<Keys, String> {

}
