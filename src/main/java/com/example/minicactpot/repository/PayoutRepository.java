package com.example.minicactpot.repository;

import com.example.minicactpot.entity.PayoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutRepository extends JpaRepository<PayoutEntity, Long> {
}