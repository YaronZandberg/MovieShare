package com.example.movieshare.constants;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Categories {
    private JsonObject Category = new JsonObject();

    public Categories(){
        this.Category.addProperty("28", "Action");
        this.Category.addProperty("12", "Adventure");
        this.Category.addProperty("16", "Animation");
        this.Category.addProperty("35", "Comedy");
        this.Category.addProperty("80", "Crime");
        this.Category.addProperty("99", "Documentary");
        this.Category.addProperty("18", "Drama");
        this.Category.addProperty("10751", "Family");
        this.Category.addProperty("14", "Fantasy");
        this.Category.addProperty("36", "History");
        this.Category.addProperty("27", "Horror");
        this.Category.addProperty("10402", "Music");
        this.Category.addProperty("9648", "Mystery");
        this.Category.addProperty("10749", "Romance");
        this.Category.addProperty("878", "Science Fiction");
        this.Category.addProperty("10770", "TV Movie");
        this.Category.addProperty("53", "Thriller");
        this.Category.addProperty("10752", "War");
        this.Category.addProperty("37", "Western");
        this.Category.addProperty("0", "Other");
    }

    public JsonElement getCategoryById(Integer id) {
        if(!this.Category.get(id.toString()).toString().isEmpty()) {
            return Category.get(id.toString());
        }
        return this.Category.get("0");
    }
}
