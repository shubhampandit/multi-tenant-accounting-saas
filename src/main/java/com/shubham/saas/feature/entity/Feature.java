package com.shubham.saas.feature.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "features")
@NoArgsConstructor
@Data
public class Feature {
    @Id
    @GeneratedValue
    private Long id;
}