package HealMeals.Api.Controller;

import HealMeals.Api.DTO.IngredientDTO;
import HealMeals.Api.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    // ✅ Create Ingredient
    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO dto) {
        IngredientDTO created = ingredientService.createIngredient(dto);
        return ResponseEntity.ok(created);
    }

    // ✅ Get All Ingredients
    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    // ✅ Get Ingredient by ID
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable UUID id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    // ✅ Update Ingredient
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable UUID id, @RequestBody IngredientDTO dto) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id, dto));
    }

    // ✅ Delete Ingredient
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable UUID id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.ok("Ingredient deleted successfully");
    }
}

