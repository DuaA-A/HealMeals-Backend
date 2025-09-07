package HealMeals.Api.DTO;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;  
    private String name;
    private String email;
    private String role;
    private String gender;
    private String dob;
    private String address;
    private String city;
    private String state;
    private String healthId;
    private String phone;
    private Instant createdAt;
    private Instant updatedAt;
}
