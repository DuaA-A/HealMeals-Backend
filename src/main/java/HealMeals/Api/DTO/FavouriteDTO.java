package HealMeals.Api.DTO;

import lombok.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

import HealMeals.Api.DTO.RecipeSummaryDto;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FavouriteDTO {
    private UUID favouriteId;
    private UUID userId;
    private RecipeSummaryDto recipe;
    private LocalDateTime addedAt;
}
