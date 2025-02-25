package com.tea.fmScout_api.controller;

import com.tea.fmScout_api.dto.ClubDto;
import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.dto.request.AddClubRequest;
import com.tea.fmScout_api.service.ClubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }


    @GetMapping
    public ResponseEntity<List<ClubDto>> getAllClubs() {
        List<ClubDto> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClubById(@PathVariable Long id) {
        return clubService.getClubById(id)
                .map(club -> new ResponseEntity<>(club, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClubDto> createClub(@Valid @RequestBody AddClubRequest createRequest) {
        ClubDto clubDto = clubService.createClub(createRequest);
        return new ResponseEntity<>(clubDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable Long id, @Valid @RequestBody AddClubRequest updateRequest) {
        ClubDto updatedClub = clubService.updateClub(id, updateRequest);
        return new ResponseEntity<>(updatedClub, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<FootballPlayerDto>> getAllPlayers(@PathVariable Long id)
    {
        List<FootballPlayerDto> playerDtos = clubService.findAllPlayers(id);
        return ResponseEntity.ok(playerDtos);
    }
}
