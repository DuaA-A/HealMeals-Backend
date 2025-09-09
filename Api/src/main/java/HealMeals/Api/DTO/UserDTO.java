package HealMeals.Api.DTO;


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
    private String name;
    private String email;
    private String role;
    private String gender;
    private LocalDate dob;
    private String address;
    private String phone;
}

