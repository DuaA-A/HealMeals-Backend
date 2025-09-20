package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.ReviewDto;
import HealMeals.Api.model.Recipe;
import HealMeals.Api.model.Review;
import HealMeals.Api.model.User;

import java.time.LocalDateTime;

public class ReviewMapper {
    public static ReviewDto toDto (Review review){
        return ReviewDto.builder()
                .recipe_id(review.getRecipe().getRecipe_id())
                .review_id(review.getReview_id())
                .comment(review.getComment())
                .date(LocalDateTime.now())
                .rating(review.getRating())
                .user_id(review.getUser().getUserId())
                .build();
    }

    public static Review toEntity(ReviewDto dto, User user, Recipe recipe) {
        return Review.builder()
                .comment(dto.getComment())
                .date(LocalDateTime.now())
                .review_id(dto.getReview_id())
                .rating(dto.getRating())
                .recipe(recipe)
                .user(user)
                .build();
    }
}
