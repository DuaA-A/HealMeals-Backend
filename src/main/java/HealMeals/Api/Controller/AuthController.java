package HealMeals.Api.Controller;

import HealMeals.Api.DTO.AuthResponseDTO;
import HealMeals.Api.DTO.RegisterRequestDTO;
import HealMeals.Api.DTO.UserDTO;
import HealMeals.Api.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import HealMeals.Api.DTO.AuthRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.registerFromDto(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
    }

    @PutMapping("/update-profile/{id}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable UUID id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(authService.updateProfile(id, dto));
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<String> updatePassword(
            @PathVariable UUID id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        authService.updatePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    // @PostMapping("/logout")
    // public ResponseEntity<String> logout() {
    //     return ResponseEntity.ok(authService.logout());
    // }
}
