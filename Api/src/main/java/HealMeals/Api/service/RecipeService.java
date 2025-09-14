 package HealMeals.Api.service;

 import HealMeals.Api.DTO.RecipeDTO;
 import HealMeals.Api.DTO.RecipeSummaryDto;
 import HealMeals.Api.Mapper.RecipeMapper;
 import HealMeals.Api.model.Ingredient;
 import HealMeals.Api.model.Recipe;
 import HealMeals.Api.model.RecipeIngredient;
 import HealMeals.Api.model.User;
 import HealMeals.Api.Repo.IngredientRepository;
 import HealMeals.Api.Repo.RecipeIngredientRepository;
 import HealMeals.Api.Repo.RecipeRepository;
 import HealMeals.Api.Repo.UserRepository;
 import jakarta.persistence.EntityNotFoundException;
 import lombok.RequiredArgsConstructor;
 import org.springframework.stereotype.Service;

 import java.time.LocalDateTime;
 import java.util.List;
 import java.util.UUID;
 import java.util.stream.Collectors;

 @Service
 @RequiredArgsConstructor
 public class RecipeService {
     private final RecipeRepository recipeRepository;
     private final RecipeIngredientRepository recipeIngredientRepository;
     private final IngredientRepository ingredientRepository;
     private final UserRepository userRepository;


     public RecipeDTO createRecipe(RecipeDTO dto, UUID createsById){
         User createdBy = userRepository.findById(createsById)
                 .orElseThrow(()->new RuntimeException("User Not Found"));

         Recipe recipe = RecipeMapper.toEntity(dto, createdBy, createdBy);
         recipe.setDateAdded(LocalDateTime.now());
         recipe.setDateUpdated(LocalDateTime.now());

         if (recipe.getRecipeIngredients() != null){
             List<RecipeIngredient> recipeIngredients = dto.getRecipeIngredients().stream()
                     .map(riDto-> {
                         Ingredient ingredient = ingredientRepository.findById(riDto.getIngredient_id())
                                 .orElseThrow(()->new EntityNotFoundException("Ingredient Not Found"));
                         RecipeIngredient ri = new RecipeIngredient();
                         ri.setRecipe(recipe);
                         ri.setIngredient(ingredient);
                         ri.setQuantity(riDto.getQuantity());
                         ri.setUnit(riDto.getUnit());
                         return ri;
                     }).collect(Collectors.toList());
             recipe.setRecipeIngredients(recipeIngredients);
         }
         Recipe saved = recipeRepository.save(recipe);
         return RecipeMapper.toDTO(saved);
     }

     public List<RecipeSummaryDto> getAllRecipes(){
         List<Recipe> recipes = recipeRepository.findAll();

         return recipes.stream()
                 .map(RecipeMapper::toSummaryDto)
                 .collect(Collectors.toList());
     }

     public RecipeDTO getRecipeById(UUID id){
         Recipe recipe = recipeRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("Recipe Not Found"));
         return RecipeMapper.toDTO(recipe);
     }

     public RecipeDTO updateRecipe(UUID recipe_id, RecipeDTO newRecipe, UUID updatedById){
         User updatedBy = userRepository.findById(updatedById)
                 .orElseThrow(()->new RuntimeException("User Not Found"));

         Recipe existingRecipe = recipeRepository.findById(recipe_id)
                 .orElseThrow(()->new RuntimeException("Recipe Not Found"));

         existingRecipe.setTitle(newRecipe.getTitle());
         existingRecipe.setDescription(newRecipe.getDescription());
         existingRecipe.setUpdatedBy(updatedBy);
         existingRecipe.setStars(newRecipe.getStars());
         existingRecipe.setPrepTime(newRecipe.getPrepTime());
         existingRecipe.setSteps(newRecipe.getSteps());
         existingRecipe.setDateUpdated(LocalDateTime.now());

         if (newRecipe.getRecipeIngredients() != null){
             existingRecipe.getRecipeIngredients().clear();

             List<RecipeIngredient> ingredients = newRecipe.getRecipeIngredients()
                     .stream().map(ridto -> {
                         Ingredient ingredient = ingredientRepository.findById(ridto.getIngredient_id())
                                 .orElseThrow(()->new RuntimeException("Ingredient Not Found"));

                         RecipeIngredient recipeIngredient = new RecipeIngredient();
                         recipeIngredient.setRecipe(existingRecipe);
                         recipeIngredient.setIngredient(ingredient);
                         recipeIngredient.setQuantity(ridto.getQuantity());
                         recipeIngredient.setUnit(ridto.getUnit());
                         return recipeIngredient;
                     })
                     .collect(Collectors.toList());
             existingRecipe.getRecipeIngredients().addAll(ingredients);
         }

         Recipe updated = recipeRepository.save(existingRecipe);
         return RecipeMapper.toDTO(updated);
     }

     public void deleteRecipe(UUID recipe_id){
         Recipe recipe = recipeRepository.findById(recipe_id).
                 orElseThrow(()->new RuntimeException("Recipe Not Found"));

         recipeRepository.delete(recipe);
     }
 }
