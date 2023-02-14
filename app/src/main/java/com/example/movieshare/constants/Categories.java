package com.example.movieshare.constants;

import android.util.Log;

import com.example.movieshare.repository.models.MovieCategory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class Categories {
    private JsonObject Categories = new JsonObject();
    private JsonObject CategoriesImages = new JsonObject();

    public Categories() {
        createLocalCategory("28", "Action", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("12", "Adventure", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("16", "Animation", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("35", "Comedy", "https://icon-library.com/images/genre-icon/genre-icon-21.jpg");
        createLocalCategory("80", "Crime", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("99", "Documentary", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("18", "Drama", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("10751", "Family", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("14", "Fantasy", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("36", "History", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("27", "Horror", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("10402", "Music", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("9648", "Mystery", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("10749", "Romance", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("878", "Science Fiction", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("10770", "TV Movie", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("53", "Thriller", "https://static.thenounproject.com/png/1994133-200.png");
        createLocalCategory("10752", "War", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("37", "Western", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
        createLocalCategory("0", "Other", "https://cdn5.vectorstock.com/i/1000x1000/62/19/comedy-cinema-or-theatre-genre-cinematography-vector-19046219.jpg");
    }

    private void createLocalCategory(String id, String name, String img)  {
        this.Categories.addProperty(id, name);
        this.CategoriesImages.addProperty(id, img);
    }

    public String getCategoryById(String id) {
        if(Objects.nonNull(this.Categories.get(id))) {
            return Categories.get(id).getAsString();
        }
        return this.Categories.get("0").getAsString();
    }

    public String getIdByName(String Name) {
        Log.d("name", Name);
        for (String localId : this.Categories.keySet()) {
            Log.d("test", this.Categories.get(localId).getAsString() + " " + Name);
            if(this.Categories.get(localId).getAsString().contentEquals(Name)) {
                Log.d("return name", localId);
                return localId;
            }
        }
        return "0";
    }

    public JsonObject getCategories() {
        return Categories;
    }

    public String getImageByName(String name) {
        String Id = getIdByName(name);
        Log.d("getImageByName", this.CategoriesImages.get(Id).getAsString());
        return CategoriesImages.get(Id).getAsString();
    }

    public JsonObject getCategoriesImages() {
        return CategoriesImages;
    }
}
