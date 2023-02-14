package com.example.movieshare.repository.models;

import static com.example.movieshare.constants.UserConstants.*;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.movieshare.context.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private Long userLastUpdate;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Ignore
    public User(@NonNull String userId, String firstName, String lastName,
                String email, String imageUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    // TODO: handle exceptions from casting or null
    public static User fromJson(Map<String, Object> json) {
        String userId = String.valueOf(json.get(USER_ID));
        String firstName = String.valueOf(json.get(USER_FIRST_NAME));
        String lastName = String.valueOf(json.get(USER_LAST_NAME));
        String email = String.valueOf(json.get(USER_EMAIL));
        String imageUrl = null;
        if (Objects.nonNull(json.get(USER_IMAGE_URL))) {
            imageUrl = String.valueOf(json.get(USER_IMAGE_URL));
        }
        User user = new User(userId, firstName, lastName, email, imageUrl);
        Timestamp lastUpdate = (Timestamp) json.get(USER_LAST_UPDATE);
        user.setUserLastUpdate(lastUpdate.getSeconds());
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> userJson = new HashMap<>();
        userJson.put(USER_ID, this.getUserId());
        userJson.put(USER_FIRST_NAME, this.getFirstName());
        userJson.put(USER_LAST_NAME, this.getLastName());
        userJson.put(USER_EMAIL, this.getEmail());
        userJson.put(USER_IMAGE_URL, this.getImageUrl());
        userJson.put(USER_LAST_UPDATE, FieldValue.serverTimestamp());
        return userJson;
    }

    public static Long getLocalLastUpdate() {
        return MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(USER_LOCAL_LAST_UPDATE, 0);
    }

    public static void setLocalLastUpdate(Long localLastUpdate) {
        MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .edit().putLong(USER_LOCAL_LAST_UPDATE, localLastUpdate).commit();
    }

    @NonNull
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getUserLastUpdate() {
        return this.userLastUpdate;
    }

    public void setUserLastUpdate(Long userLastUpdate) {
        this.userLastUpdate = userLastUpdate;
    }
}
