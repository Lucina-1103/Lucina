package com.example.helloworldsample.repository;

import com.example.helloworldsample.entity.HelloworldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloworldRepository extends JpaRepository<HelloworldEntity, Long> {
}