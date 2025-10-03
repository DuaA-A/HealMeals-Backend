package HealMeals.Api.service;

import HealMeals.Api.DTO.RegisterRequestDTO;
import HealMeals.Api.DTO.UserDTO;
import HealMeals.Api.Mapper.UserMapper;
import HealMeals.Api.JwtUtil;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.User;
import lombok.RequiredArgsConstructor;
import HealMeals.Api.DTO.AuthResponseDTO;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.UUID;

import HealMeals.Api.DTO.AuthResponseDTO;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // public AuthService(UserRepository userRepository,
    //                     PasswordEncoder passwordEncoder,
    //                     JwtUtil jwtUtil) {
    //     this.userRepository = userRepository;
    //     this.passwordEncoder = passwordEncoder;
    //     this.jwtUtil = jwtUtil;
    // }

    public AuthResponseDTO registerFromDto(RegisterRequestDTO dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .gender(dto.getGender())
                .dob(dto.getDob())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
        user = userRepository.save(user);
        // FIX: Pass both email + role, since JwtUtil expects 2 params
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return AuthResponseDTO.builder()
                .token(token)
                .user(UserMapper.toDTO(user))
                .build();
    }

    public AuthResponseDTO login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return AuthResponseDTO.builder()
                .token(token)
                .user(UserMapper.toDTO(user))
                .build();
    }

    public UserDTO updateProfile(UUID userId, UserDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getGender() != null) user.setGender(dto.getGender());
        if (dto.getAddress() != null) user.setAddress(dto.getAddress());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getDob() != null) user.setDob(dto.getDob());
        userRepository.save(user);
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .dob(user.getDob())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }

    public void updatePassword(UUID userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // public String logout() {
    //     return "Successfully logged out (discard JWT on client).";
    // }
}
