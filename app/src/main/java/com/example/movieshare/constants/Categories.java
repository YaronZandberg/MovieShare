package com.example.movieshare.constants;
import com.example.movieshare.R;
import com.google.gson.JsonObject;
import java.util.Objects;

public class Categories {
    private final JsonObject Categories = new JsonObject();
    private final JsonObject CategoriesImages = new JsonObject();

    public Categories() {
        createLocalCategory("28", "Action", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("12", "Adventure", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("16", "Animation", R.drawable.ic_baseline_animation_24);
        createLocalCategory("35", "Comedy", R.drawable.ic_baseline_theater_comedy_24);
        createLocalCategory("80", "Crime", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("99", "Documentary", R.drawable.ic_baseline_camera_roll_24);
        createLocalCategory("18", "Drama", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("10751", "Family", R.drawable.ic_baseline_family_restroom_24);
        createLocalCategory("14", "Fantasy", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("36", "History", R.drawable.ic_baseline_history_24);
        createLocalCategory("27", "Horror", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("10402", "Music", R.drawable.ic_baseline_music_note_24);
        createLocalCategory("9648", "Mystery", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("10749", "Romance", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("878", "Science Fiction", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("10770", "TV Movie", R.drawable.ic_baseline_tv_24);
        createLocalCategory("53", "Thriller", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("10752", "War", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("37", "Western", R.drawable.ic_baseline_local_movies_24);
        createLocalCategory("0", "Other", R.drawable.ic_baseline_local_movies_24);
    }

    private void createLocalCategory(String id, String name, Integer img)  {
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
        for (String localId : this.Categories.keySet()) {
            if(this.Categories.get(localId).getAsString().contentEquals(Name)) {
                return localId;
            }
        }
        return "0";
    }

    public JsonObject getCategories() {
        return Categories;
    }

    public Integer getImageByName(String name) {
        String Id = getIdByName(name);
        return CategoriesImages.get(Id).getAsInt();
    }
}
