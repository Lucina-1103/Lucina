package com.example.helloworldsample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Table(name = "m_payout")
@Data
public class PayoutEntity {
    @Id
    @Column(name = "id")
    private Short id;

    @Column(name = "sum")
    private Short sum;

    @Column(name = "mgp")
    private Short mgp;
}