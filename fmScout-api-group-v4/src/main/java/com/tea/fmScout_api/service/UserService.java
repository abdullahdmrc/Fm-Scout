package com.tea.fmScout_api.service;

import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.dto.converter.FootballPlayerDtoConverter;
import com.tea.fmScout_api.model.User;
import com.tea.fmScout_api.repository.FavouriteRepository;
import com.tea.fmScout_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final FavouriteService favouriteService;
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final FootballPlayerDtoConverter footballPlayerDtoConverter;

    public UserService(FavouriteService favouriteService, FavouriteRepository favouriteRepository, UserRepository userRepository, FootballPlayerDtoConverter footballPlayerDtoConverter)
    {
        this.favouriteService = favouriteService;
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
        this.footballPlayerDtoConverter = footballPlayerDtoConverter;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "userId"));
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public boolean authenticateUser(String userName, String password) {
        return userRepository.findByUserName(userName)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }


    @Transactional
    public User createUser(User user)
    {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("The User is already exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserName(updatedUser.getUserName());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    @Transactional
    public void deleteUser(Long userId)
    {
        userRepository.deleteById(userId);
    }

    public String generateToken(String name) {

        return "token123";
    }


    public Object isAdmin(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı: " + userName));
        return user.getIsAdmin();
    }


    public List<FootballPlayerDto> getAllFavoritedPlayers(Long userId)
    {
        return favouriteRepository.findFootballPlayerByUser_UserId(userId).stream()
                .map(footballPlayerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public Optional<User> getUserIdByUserName(String userName){
        return userRepository.findIdByUserName(userName);
    }

    public List<User> getNonAdminUsers() {
        return userRepository.findByIsAdminFalse();
    }

}

