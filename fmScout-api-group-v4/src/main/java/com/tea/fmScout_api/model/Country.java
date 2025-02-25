package com.tea.fmScout_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "countries")
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "federation", nullable = false)
    private String federation;

}
