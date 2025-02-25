package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players_roles")
@Data
@NoArgsConstructor
public class PlayerRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerRoleId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private FootballPlayer player;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    public PlayerRole(FootballPlayer player, Position position)
    {
        this.player = player;
        this.position = position;
    }
}