package HealMeals.Api.Controller;

import HealMeals.Api.DTO.RegisterRequestDTO;
import HealMeals.Api.DTO.UserDTO;
import HealMeals.Api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.registerFromDto(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String token = authService.login(credentials.get("email"), credentials.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
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
