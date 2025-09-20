package HealMeals.Api.service;

import HealMeals.Api.DTO.ReviewDto;
import HealMeals.Api.Mapper.ReviewMapper;
import HealMeals.Api.Repo.RecipeRepository;
import HealMeals.Api.Repo.ReviewRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.Review;
import HealMeals.Api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

    public ReviewDto createReview (ReviewDto dto) {
        Recipe recipe = recipeRepository.findById(dto.getRecipe_id())
                .orElseThrow(()->new RuntimeException("Recipe Not Found"));

        User user = userRepository.findById(dto.getUser_id())
                .orElseThrow(()->new RuntimeException("User Not Found"));

        Review review = ReviewMapper.toEntity(dto, user, recipe);
        return ReviewMapper.toDto(reviewRepository.save(review));
    }
}
