package com.tea.fmScout_api.controller;

import com.tea.fmScout_api.model.Favourite;
import com.tea.fmScout_api.service.FavouriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping
    public ResponseEntity<Favourite> addFavourite(@RequestParam Long userId, @RequestParam Long playerId) {

        Favourite favourite = favouriteService.addFavourite(userId, playerId);
        return ResponseEntity.ok(favourite);
    }

    @DeleteMapping
    public ResponseEntity<String> removeFavourite(@RequestParam Long userId, @RequestParam Long playerId) {
        favouriteService.removeFavourite(userId, playerId);
        return ResponseEntity.ok("Favourite removed successfully.");
    }
}
