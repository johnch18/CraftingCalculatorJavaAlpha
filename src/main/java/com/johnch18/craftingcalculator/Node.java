package com.johnch18.craftingcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {

    private Node parent;
    private final List<Node> children;
    private Ingredient contents;

    /*
    * Methods
    * */

    public Node(Ingredient ingredient) {
        this(ingredient, (Node) null);
    }

    public Node(Ingredient ingredient, Node parent) {
        this(ingredient, parent, new ArrayList<>());
    }

    public Node(Ingredient ingredient, ArrayList<Node> children) {
        this(ingredient, null, children);
    }

    public Node(Ingredient ingredient, Node parent, ArrayList<Node> children) {
        setContents(ingredient);
        setParent(parent);
        this.children = children;
    }

    public void generateChildren() {
        generateChildren(new IngredientList());
    }

    public void generateChildren(IngredientList cache) {
        // TODO: Factor in already attained items
        Recipe recipe = contents.getComponent().getActiveRecipe();
        if (recipe == null || !recipe.isEnabled())
            return;
        //
        recipe.subtractFromCache(contents, cache);
        if (contents.getAmount() <= 0)
            return;
        //
        Ingredient targetIngredient = recipe.getOutputIngredient(contents);
        if (targetIngredient == null)
            return;
        //
        int numberOfCrafts = recipe.calculateNumberOfCrafts(contents, targetIngredient);
        recipe.factorInOutputs(contents, numberOfCrafts, cache);
        //
        for (Map.Entry<String, Ingredient> entry: recipe.getInputs().getIterator()) {
            Ingredient modified = entry.getValue().multiply(numberOfCrafts);
            Node node = new Node(modified, this);
            getChildren().add(node);
        }
        for (Node node: getChildren()) {
            node.generateChildren(cache);
        }
    }

    public String render() {
        return render(0);
    }

    public String render(int depth) {
        StringBuilder sb = new StringBuilder();
        if (contents.isValid()) {
            for (int i = 0; i < depth; i++) {
                sb.append("        ");
            }
            sb.append("*");
            sb.append("-------");
            if (hasChildren())
                sb.append("*");
            else
                sb.append("-");
            sb.append(" ");
            sb.append(contents.toStringFancy());
            sb.append("\n");
            for (Node child : getChildren()) {
                sb.append(child.render(depth + 1));
            }
        }
        return sb.toString();
    }

    public boolean hasChildren() {
        return getChildren().size() > 0;
    }

    /*
    * Getters and setters
    * */

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Ingredient getContents() {
        return contents;
    }

    public void setContents(Ingredient contents) {
        this.contents = contents;
    }

}
