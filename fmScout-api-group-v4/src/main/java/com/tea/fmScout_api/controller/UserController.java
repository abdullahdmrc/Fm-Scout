package com.tea.fmScout_api.controller;

import com.tea.fmScout_api.dto.ClubDto;
import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.model.User;
import com.tea.fmScout_api.service.FavouriteService;
import com.tea.fmScout_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final FavouriteService favouriteService;
    private final UserService userService;


    public UserController(UserService userService, FavouriteService favouriteService)
    {
        this.userService = userService;
        this.favouriteService = favouriteService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/non-admins")
    public List<User> getNonAdminUsers() {
        return userService.getNonAdminUsers();
    }
    @PostMapping("/signup")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String userName, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticateUser(userName, password);

        Map<String, Object> response = new HashMap<>();
        if (isAuthenticated) {
            response.put("message", "Successfully login");
            response.put("userId",userService.getUserIdByUserName(userName));
            //response.put("isAdmin", userService.isAdmin(userName)); // Örnek olarak bir `isAdmin` kontrolü
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            response.put("message", "Invalid name or password");
            return ResponseEntity.status(401).body(response);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Var olan oturumu al, yoksa null döner
        if (session != null) {
            session.invalidate(); // Oturumu sonlandır
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully logged out");

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

    }

    @GetMapping("/{id}/favourites")
    public ResponseEntity<List<FootballPlayerDto>> getAllFavoritedPlayers(@PathVariable Long id)
    {
        List<FootballPlayerDto> footballPlayerDtoList  = userService.getAllFavoritedPlayers(id);
        return ResponseEntity.ok(footballPlayerDtoList);
    }
}
