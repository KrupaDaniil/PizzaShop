package com.example.pizzashop.Models;


import java.util.List;

public class Pizza {
    private int id;
    private String name;
    private String ingredients;

    public Pizza() {}

    public Pizza(String name, String ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Pizza(int id, String name, String ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }
}
