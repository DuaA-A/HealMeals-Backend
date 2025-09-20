package HealMeals.Api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import HealMeals.Api.DTO.ReviewDTO;
import HealMeals.Api.Repo.RecipeRepository;
import HealMeals.Api.Repo.ReviewRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.Review;
import HealMeals.Api.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public ReviewDTO create(ReviewDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        Review review = Review.builder()
                .user(user)
                .recipe(recipe)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .date(LocalDateTime.now())
                .build();

        return toDTO(reviewRepository.save(review));
    }

    public List<ReviewDTO> getAll() {
        return reviewRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO getById(UUID id) {
        return reviewRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void delete(UUID id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewId(review.getReviewId());
        dto.setUserId(review.getUser().getUserId());
        dto.setRecipeId(review.getRecipe().getRecipeId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setDate(review.getDate());
        return dto;
    }
}