package HealMeals.Api.service;

import HealMeals.Api.DTO.FavouriteDTO;
import HealMeals.Api.DTO.RecipeSummaryDto;
import HealMeals.Api.Repo.FavouriteRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.Repo.RecipeRepository;
import HealMeals.Api.model.Favourite;
import HealMeals.Api.model.User;
import HealMeals.Api.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public FavouriteDTO addFavourite(UUID userId, UUID recipeId) {
        if (favouriteRepository.existsByUser_UserIdAndRecipe_RecipeId(userId, recipeId)) {
            throw new RuntimeException("Already favourited!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        Favourite favourite = Favourite.builder()
                .user(user)
                .recipe(recipe)
                .addedAt(LocalDateTime.now())
                .build();

        favouriteRepository.save(favourite);

        return toDto(favourite);
    }

    @Transactional
    public void removeFavourite(UUID userId, UUID recipeId) {
        favouriteRepository.deleteByUser_UserIdAndRecipe_RecipeId(userId, recipeId);
    }

    public List<FavouriteDTO> getFavouritesByUser(UUID userId) {
        return favouriteRepository.findByUser_UserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    private FavouriteDTO toDto(Favourite favourite) {
        return FavouriteDTO.builder()
                .favouriteId(favourite.getFavouriteId())
                .userId(favourite.getUser().getUserId()) 
                .recipe(RecipeSummaryDto.builder()
                        .recipeId(favourite.getRecipe().getRecipeId())
                        .name(favourite.getRecipe().getName())
                        .description(favourite.getRecipe().getDescription())
                        .prepTimeMinutes(favourite.getRecipe().getPrepTimeMinutes())
                        .build())
                .addedAt(favourite.getAddedAt())
                .build();
    }

}
