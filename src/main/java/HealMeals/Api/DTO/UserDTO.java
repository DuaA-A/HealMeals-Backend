package HealMeals.Api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID userId;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String role;
    private String gender;
    private LocalDate dob;
    private String address;
    private String phone;

    // Note: password intentionally not included in this DTO for security.
}
