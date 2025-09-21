package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.ReviewDTO;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.Review;
import HealMeals.Api.model.User;

import java.time.LocalDateTime;

public class ReviewMapper {

    public static ReviewDTO toDto(Review review) {
        if (review == null) return null;

        return ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .recipeId(review.getRecipe() != null ? review.getRecipe().getRecipeId() : null)
                .comment(review.getComment())
                .date(review.getDate())
                .rating(review.getRating())
                .userId(review.getUser() != null ? review.getUser().getUserId() : null)
                .build();
    }

    public static Review toEntity(ReviewDTO dto, User user, Recipe recipe) {
        if (dto == null) return null;

        return Review.builder()
                .reviewId(dto.getReviewId())
                .comment(dto.getComment())
                .date(dto.getDate() != null ? dto.getDate() : LocalDateTime.now())
                .rating(dto.getRating())
                .recipe(recipe)
                .user(user)
                .build();
    }
}
