package com.example.demo.data;

import com.example.demo.domain.entities.Keys;
import com.example.demo.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeysRepository extends JpaRepository<Keys, String> {

}
