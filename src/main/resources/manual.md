# Crafting Calculator Java Edition
## Preamble-Ramble
If you need help, use the `help` command or read this manual.
If anything is missing or opaque, let the author know.

## How It Works
There are many moving parts in this program, so I will go through and elucidate them.

- ### `Component`
  `Component` objects are conceptually anything that can be created in Minecraft.
  This could include items, fluids, even things like multi-block pieces.
  A `Component` contains several pieces of information:
  1. `name`: Works as an identifier within the program.
  It is case-sensitive and must not contain whitespace or symbols.
  Keep it simple.
  2. `isFluid`: Indicates whether a `Component` is a fluid or an item.
  This field is only used for cosmetic things, but it's not completely useless.
  3. `fancyName`: This is the more human-readable identifier, and is optional, however like `isFluid`, it is useful cosmetically.
  If unspecified, defaults to be the same as `name`.
  4. `recipes`: A list of `Recipe` objects that output this component.
  It should be noted that only one recipe should be active at a given time, doing otherwise is undefined behavior.
  
  Creation of `Component` objects is tightly controlled.
  If you create two different `Component` objects with the same name using the provided factory method, `Component.getComponent`, you will receive two copies of the same object.
  
  Serialized in JSON as:
  `{'name':'netherStar', 'isFluid':false, 'fancyName':'Nether Star'}`.

- ### `Ingredient`
  `Ingredient` objects act similarly to `ItemStack` objects in Minecraft.
  Essentially, they are a component plus the following information:
  1. `amount`: The number of components present in the `Ingredient`.
  Must be a whole number.
  2. `chance`: The percent chance of getting `amount`.
  Is a double.
  3. `enabled`: Whether the `Ingredient` should be factored into calculations.
  Presently not used.
  
  `Ingredient` objects are serialized as strings. 
  The following are valid `Ingredient` strings:
  - `slimeball`     -> 1 Slime Ball 100% of the time.
  - `netherStar:2`  -> 2 Nether Stars 100% of the time.
  - `diamond:1:0.1` -> 1 Diamond 10% of the time.
  - `lava:1000L` -> 1 bucket of Lava
  
  Note that if you want to include `chance`, you must specify `amount`.
  Including an L in amount is used to signify a fluid.

- ### `Recipe`
  A `Recipe` is fairly straightforward, it stores an `IngredientList` of inputs and one of outputs.
  It is a simple mapping.
  Additionally, a `Recipe`can store whether it is enabled, which functions to tell the algorithm whether to ignore it or not.
  
  Recipes are serialized in a simple JSON format, with a list of inputs, a list of outputs, and whether it is enabled:
  `{'inputs':[], 'outputs':[], 'enabled':true}`

- ### `IngredientList`
  An `IngredientList` is a dictionary of `String` -> `Ingredient` objects that automatically combines identical stacks and removes empty ones.
  Simply serialized as a list of `Ingredient` objects, not even explicitly.

- ### `CostResult`
  Simply a pair of `IngredientList` objects, used to allow multiple returns from the algorithm.

- ### `Node`
  Used in the construction of recipe trees, stores an `Ingredient` as its data.
  Can be printed.

- ### `RecipeAlgorithm`
  A static class that contains all code used for calculations.
  Purely there for cleanliness and outlining.

- ### `RecipeBook`
  Simply a collection of `Recipe` objects, is the root of serialization, containing the list of `Component` objects as well as `Recipe` objects.

- ### `RecipeREPL`
  CLI wrapper for RecipeBook.

- ### `REPLDialog`
  Contains code for interfacing with the user and generalizing different messages.
  Again, an exercise in outlining.

- ### `Command`(s)
  Simply store command information and execution code.
  Not really designed for extensibility, more just a way to streamline the process of setting up the REPL.
  I just really dislike `switch` and `if/else` blocks if I can avoid them.

- ### `Exception`(s)
  Kind of self-explanatory.
  A base exception, branches into critical and noncritical exceptions.
  The former means I fucked up, the latter means you fucked up.