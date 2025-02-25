package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "is_Admin",  columnDefinition ="BOOLEAN DEFAULT FALSE" )
    private Boolean isAdmin=false;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;
}
