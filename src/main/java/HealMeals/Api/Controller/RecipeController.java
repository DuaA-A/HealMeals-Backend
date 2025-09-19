 package HealMeals.Api.Controller;

 import HealMeals.Api.DTO.RecipeDTO;
 import HealMeals.Api.DTO.RecipeSummaryDto;
 import HealMeals.Api.model.Recipe;
 import lombok.RequiredArgsConstructor;
 import HealMeals.Api.service.RecipeService;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;
 import java.util.UUID;

 @RequiredArgsConstructor
 @RestController
 @RequestMapping("api/recipes")
 public class RecipeController {
     private final RecipeService recipeService;

     @PostMapping
     public ResponseEntity<RecipeDTO> createRecipe
             (@RequestBody RecipeDTO dto, @RequestParam UUID createdById){
         RecipeDTO created = recipeService.createRecipe(dto, createdById);
         return ResponseEntity.ok(created);
     }

     @GetMapping
     public ResponseEntity<List<RecipeSummaryDto>> getALLRecipes(){
         List<RecipeSummaryDto> recipeDTOS = recipeService.getAllRecipes();
         return ResponseEntity.ok(recipeDTOS);
     }

     @GetMapping("/{recipe_id}")
     public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable UUID recipe_id){
         RecipeDTO recipe = recipeService.getRecipeById(recipe_id);
         return ResponseEntity.ok(recipe);
     }

     @PutMapping("/{recipe_id}")
     public ResponseEntity<RecipeDTO> updateRecipe
             (@PathVariable UUID recipe_id, @RequestBody RecipeDTO recipeDTO, @RequestParam UUID updatedById){
         RecipeDTO updatedRecipe = recipeService.updateRecipe(recipe_id, recipeDTO, updatedById);
         return ResponseEntity.ok(updatedRecipe);
     }

     @DeleteMapping("/{recipe_id}")
     public ResponseEntity<String> deleteRecipe(@PathVariable UUID recipe_id){
         recipeService.deleteRecipe(recipe_id);
         return ResponseEntity.ok("Recipe Deleted Successfully");
     }
 }
