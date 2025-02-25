package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "positions")
@Data
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;

    @Column(name = "position_name", nullable = false)
    private String positionName;
}
