package HealMeals.Api.Controller;

import HealMeals.Api.DTO.FavouriteDTO;
import HealMeals.Api.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favourites")
@RequiredArgsConstructor
public class FavouriteController {

    private final FavouriteService favouriteService;

    @PostMapping("/{userId}/{recipeId}")
    public ResponseEntity<FavouriteDTO> addFavourite(
            @PathVariable UUID userId,
            @PathVariable UUID recipeId) {
        return ResponseEntity.ok(favouriteService.addFavourite(userId, recipeId));
    }

    @DeleteMapping("/{userId}/{recipeId}")
    public ResponseEntity<String> removeFavourite(
            @PathVariable UUID userId,
            @PathVariable UUID recipeId) {
        favouriteService.removeFavourite(userId, recipeId);
        return ResponseEntity.ok("Removed from favourites");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavouriteDTO>> getFavouritesByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(favouriteService.getFavouritesByUser(userId));
    }
}
