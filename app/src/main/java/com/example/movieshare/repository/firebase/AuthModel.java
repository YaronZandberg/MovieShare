package com.example.movieshare.repository.firebase;

import com.example.movieshare.listeners.users.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthModel {
    private final FirebaseAuth firebaseAuth;

    public AuthModel() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void register(String email, String password, RegisterListener registerListener) {
        this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().getUser() != null) {
                        registerListener.onComplete(task.getResult().getUser().getUid());
                    }
                });
    }

    public void login(String email, String password,
                      LoginOnSuccessListener onSuccessListener,
                      LoginOnFailureListener onFailureListener) {
        this.firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(task -> onSuccessListener.onComplete())
                .addOnFailureListener(command -> onFailureListener.onComplete(command.getMessage()));
    }

    public void logout(LogoutListener logoutListener) {
        this.firebaseAuth.signOut();
        logoutListener.onComplete();
    }

    public boolean isSignedIn() {
        FirebaseUser currentUser = this.firebaseAuth.getCurrentUser();
        return (Objects.nonNull(currentUser));
    }

    public String getCurrentUserUid() {
        if (isSignedIn()) {
            return this.firebaseAuth.getCurrentUser().getUid();
        }
        return null;
    }

    public void isEmailExist(String email,
                             IsEmailExistOnSuccessListener onSuccessListener,
                             IsEmailExistOnFailureListener onFailureListener) {
        this.firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnSuccessListener(task -> {
                    if (task.getSignInMethods() != null) {
                        boolean isNewUser = task.getSignInMethods().isEmpty();
                        onSuccessListener.onComplete(!isNewUser);
                    }
                })
                .addOnFailureListener(command -> onFailureListener.onComplete(command.getMessage()));
    }
}
