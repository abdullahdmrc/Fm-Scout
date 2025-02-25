package com.tea.fmScout_api.controller;

import com.tea.fmScout_api.dto.ClubDto;
import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.dto.LeagueDto;
import com.tea.fmScout_api.dto.request.AddLeagueRequest;;
import com.tea.fmScout_api.service.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService)
    {
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<List<LeagueDto>> getAllLeagues()
    {
        List<LeagueDto> leagues = leagueService.getAllLeagues();
        return new ResponseEntity<>(leagues, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDto> getLeagueById(@PathVariable Long id) {
        return leagueService.getLeagueById(id)
                .map(league -> new ResponseEntity<>(league, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LeagueDto> createLeague(@Valid @RequestBody AddLeagueRequest createRequest) {
        LeagueDto leagueDto = leagueService.createLeague(createRequest);
        return new ResponseEntity<>(leagueDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueDto> updateLeague(@PathVariable Long id, @Valid @RequestBody AddLeagueRequest updateRequest)
    {
        LeagueDto updatedLeague = leagueService.updateLeague(id, updateRequest);
        return new ResponseEntity<>(updatedLeague, HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteLeague(@PathVariable Long id) {
//        leagueService.deleteLeague(id);
//        return ResponseEntity.noContent().build();
//    }
    @GetMapping("/{id}/clubs")
    public ResponseEntity<List<ClubDto>> getAllClubs(@PathVariable Long id)
    {
        List<ClubDto> clubDtoList = leagueService.findAllClubs(id);
        return ResponseEntity.ok(clubDtoList);
    }
}
