# Crafting Calculator vAlpha

## Supports:

- Recursive crafting
- Multiple recipes (must manually select a recipe to use though, give me a break I'm not going to read your mind)
- Chance outputs
- Fluids
- Dummy recipes

## Features I want:

- Ability to rip recipes, item names, and icons from game
- GUI and/or CLI to do crafting calculations
- Minecraft version independence
- Portability

## Example:

Given a recipe tree, rough approximation of NH's star recipe, simplified for demonstrative purposes:

```java
class Test {

    public static void main(String[] args) {
        RecipeBook recipeBook = new RecipeBook();
        recipeBook.addRecipe(
                new String[]{"netherStar:2"},
                new String[]{"tinyNetherStarDust", "magmaCream"}
        );
        recipeBook.addRecipe(
                new String[]{"tinyNetherStarDust:9"},
                new String[]{"netherStarDust"}
        );
        recipeBook.addRecipe(
                new String[]{"netherStarDust"},
                new String[]{"netherStar"}
        );
        //
        CostResult results = Component.createComponent("netherStar").getCostOf(64, new IngredientList(new String[]{
                "tinyNetherStarDust:1"
        }));
        display(results);
    }

    private static void display(CostResult result) {
        System.out.println("Inputs:");
        for (Map.Entry<String, Ingredient> entry : result.getCost().getIterator()) {
            System.out.print('\t');
            System.out.println(entry.getValue().toString());
        }
        System.out.println("Leftovers:");
        for (Map.Entry<String, Ingredient> entry : result.getExcess().getIterator()) {
            System.out.print('\t');
            System.out.println(entry.getValue().toString());
        }
    }

}
```
We would get the following output:
```
Inputs:
	magmaCream:34:1.0
Leftovers:
	tinyNetherStarDust:2:1.0
	netherStar:1:1.0
```
Which is correct.

## Current Limitations
- Can only use one recipe per output, must select between them to do calculations.
- Presently no metadata support
- NBT support highly unlikely
- No convenient interface
- Not hooked into MC yet