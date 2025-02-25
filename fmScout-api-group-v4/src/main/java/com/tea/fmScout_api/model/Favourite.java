package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favourites")
@Data
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favouriteId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    private FootballPlayer player;
}
