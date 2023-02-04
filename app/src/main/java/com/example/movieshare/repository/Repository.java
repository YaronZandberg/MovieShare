package com.example.movieshare.repository;

import com.example.movieshare.repository.firebase.FirebaseModel;
import com.example.movieshare.repository.room.LocalModel;

public class Repository {
    public static final Repository repositoryInstance = new Repository();
    private final LocalModel localModel = new LocalModel();
    private final FirebaseModel firebaseModel = new FirebaseModel();

    private Repository() {
    }

    public static Repository getRepositoryInstance() {
        return repositoryInstance;
    }

    public LocalModel getLocalModel() {
        return this.localModel;
    }

    public FirebaseModel getFirebaseModel() {
        return this.firebaseModel;
    }
}
