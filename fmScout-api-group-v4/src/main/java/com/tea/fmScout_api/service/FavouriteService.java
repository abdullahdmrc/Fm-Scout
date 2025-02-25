package com.tea.fmScout_api.service;

import com.tea.fmScout_api.model.Favourite;
import com.tea.fmScout_api.model.FootballPlayer;
import com.tea.fmScout_api.model.User;
import com.tea.fmScout_api.repository.FavouriteRepository;
import com.tea.fmScout_api.repository.FootballPlayerRepository;
import com.tea.fmScout_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final FootballPlayerRepository footballPlayerRepository;
    private final UserRepository userRepository;

    public FavouriteService(FavouriteRepository favouriteRepository,
                            FootballPlayerRepository footballPlayerRepository,
                            UserRepository userRepository) {
        this.favouriteRepository = favouriteRepository;
        this.footballPlayerRepository = footballPlayerRepository;
        this.userRepository = userRepository;
    }

    public List<Favourite> getFavouritesByUser(Long userId)
    {
        return favouriteRepository.findByUser_UserId(userId);
    }

    @Transactional
    public Favourite addFavourite(Long userId, Long playerId)
    {
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        FootballPlayer footballPlayer = footballPlayerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));

        if (favouriteRepository.existsByUser_UserIdAndPlayer_PlayerId(userId, playerId)) {
            throw new RuntimeException("This player is already in the user's favourites.");
        }

        Favourite favourite = new Favourite();
        favourite.setUser(user);
        favourite.setPlayer(footballPlayer);
        return favouriteRepository.save(favourite);
    }

    @Transactional
    public void removeFavourite(Long userId, Long playerId)
    {
        Favourite favourite = favouriteRepository.findByUser_UserIdAndPlayer_PlayerId(userId, playerId)
                .orElseThrow(() -> new RuntimeException("Favourite not found for the given user and player."));
        favouriteRepository.delete(favourite);
    }
}

