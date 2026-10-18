package com.restaurant.facade;

import java.util.Map;

public class MenuItem {
    private final String name;
    private final int price;
    private final int cookMinutes;
    private final Map<String, Integer> recipe;

    public MenuItem(String name, int price, int cookMinutes, Map<String, Integer> recipe) {
        this.name = name;
        this.price = price;
        this.cookMinutes = cookMinutes;
        this.recipe = recipe;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getCookMinutes() { return cookMinutes; }
    public Map<String, Integer> getRecipe() { return recipe; }
}
