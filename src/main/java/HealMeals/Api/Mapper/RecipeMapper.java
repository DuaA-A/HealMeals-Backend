 package HealMeals.Api.Mapper;

 import HealMeals.Api.DTO.RecipeDTO;
 import HealMeals.Api.DTO.RecipeSummaryDto;
 import HealMeals.Api.model.Recipe;
 import HealMeals.Api.model.RecipeIngredient;
 import HealMeals.Api.model.User;

 import java.util.stream.Collectors;

 public class RecipeMapper {
     public static Recipe toEntity(RecipeDTO dto, User updatedBy, User createdBy) {
         if (dto == null) return null;

         Recipe recipe = Recipe.builder()
                 .recipe_id(dto.getRecipe_id())
                 .description(dto.getDescription())
                 .stars(dto.getStars())
                 .steps(dto.getSteps())
                 .title(dto.getTitle())
                 .createdBy(createdBy)
                 .updatedBy(updatedBy)
                 .dateAdded(dto.getDateAdded())
                 .dateUpdated(dto.getDateUpdated())
                 .prepTime(dto.getPrepTime())
                 .build();

         if (dto.getRecipeIngredients() != null) {
             recipe.setRecipeIngredients(
                     dto.getRecipeIngredients().stream()
                             .map(riDto -> {
                                 RecipeIngredient ri = RecipeIngredientMapper.toEntity(riDto, null);
                                 ri.setRecipe(recipe);
                                 return ri;
                             })
                             .collect(Collectors.toList())
             );
         }

         return recipe;
     }


     public static RecipeDTO toDTO(Recipe recipe){
         if (recipe == null) return null;

         RecipeDTO dto = RecipeDTO.builder()
                 .recipe_id(recipe.getRecipe_id())
                 .description(recipe.getDescription())
                 .title(recipe.getTitle())
                 .stars(recipe.getStars())
                 .steps(recipe.getSteps())
                 .createdBy(recipe.getCreatedBy().getUserId()!=null?recipe.getCreatedBy().getUserId():null)
                 .updatedBy(recipe.getUpdatedBy().getUserId()!=null?recipe.getUpdatedBy().getUserId():null)
                 .dateAdded(recipe.getDateAdded())
                 .dateUpdated(recipe.getDateUpdated())
                 .prepTime(recipe.getPrepTime())
                 .recipeIngredients(recipe.getRecipeIngredients()!=null
                         ?recipe.getRecipeIngredients().stream()
                         .map(RecipeIngredientMapper::toDto)
                         .collect(Collectors.toList()) : null)
                 .build();
         return dto;
     }

     public static RecipeSummaryDto toSummaryDto(Recipe recipe){
         return new RecipeSummaryDto(
                 recipe.getRecipe_id(),
                 recipe.getTitle(),
                 recipe.getDescription(),
                 recipe.getStars()
         );
     }
 }
