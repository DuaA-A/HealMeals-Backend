package HealMeals.Api.service;

import HealMeals.Api.DTO.RegisterRequestDTO;
import HealMeals.Api.DTO.UserDTO;
import HealMeals.Api.JwtUtil;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // injected

    public User registerFromDto(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole() == null ? "ROLE_USER" : dto.getRole())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();

        if (dto.getDob() != null) {
            try {
                user.setDob(LocalDate.parse(dto.getDob()));
            } catch (DateTimeParseException ignored) {
            }
        }

        return userRepository.save(user);
    }

    // Use AuthenticationManager to authenticate (preferred)
    public String login(String email, String password) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Invalid credentials");
        }

        // at this point authentication succeeded; load user role
        User user = userRepository.findByEmail(email).orElseThrow();
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    // profile update etc (unchanged)
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
