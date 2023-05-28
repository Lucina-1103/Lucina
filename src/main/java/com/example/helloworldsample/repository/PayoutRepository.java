package com.example.helloworldsample.repository;

import com.example.helloworldsample.entity.PayoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutRepository extends JpaRepository<PayoutEntity, Long> {
}