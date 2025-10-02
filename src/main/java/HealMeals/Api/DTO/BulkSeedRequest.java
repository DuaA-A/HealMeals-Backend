// package HealMeals.Api.Controller;

// import HealMeals.Api.model.ProfileCondition;
// import lombok.Data;

// import java.util.List;

// @Data
// public class BulkSeedRequest {
//     private List<Condition> conditions;
//     private List<String> ingredients;
//     private List<RecipeData> recipes;

//     @Data
//     public static class Condition {
//         private String conditionName;
//         private ProfileCondition.ConditionType conditionType;
//     }

//     @Data
//     public static class RecipeData {
//         private String name;
//         private String description;
//         private String summary;
//         private int prepTimeMinutes;
//         private List<RecipeIngredientData> ingredients;
//         private List<String> steps;
//     }

//     @Data
//     public static class RecipeIngredientData {
//         private String name;
//         private double quantity;
//         private String unit;
//     }
// }
