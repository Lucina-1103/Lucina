package com.example.minicactpot.repository;

import com.example.minicactpot.entity.HelloworldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloworldRepository extends JpaRepository<HelloworldEntity, Long> {
}