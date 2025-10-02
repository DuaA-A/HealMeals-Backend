package HealMeals.Api.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteDTO {
    private UUID favouriteId;
    private UUID userId;
    private RecipeSummaryDto recipe;
    private LocalDateTime addedAt;
}
