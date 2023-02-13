package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.UserConstants.*;

import android.graphics.Bitmap;

import com.example.movieshare.listeners.authentication.*;
import com.example.movieshare.listeners.movies.GetMovieItemListener;
import com.example.movieshare.repository.models.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserExecutor {
    private static final UserExecutor userExecutorInstance = new UserExecutor();
    public static final String IMAGE_FOLDER = "users";
    private final FirebaseFirestore db;

    // TODO: Add FirebaseStorage after implementing working with firebase storage

    private UserExecutor() {
        this.db = FirebaseFirestore.getInstance();
    }

    public static UserExecutor instance() {
        return userExecutorInstance;
    }

    public void getAllUsers(GetAllUsersListener listener) {
        this.db.collection(USER_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<User> users = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = User.fromJson(document.getData());
                            users.add(user);
                        }
                    }
                    listener.onComplete(users);
                });
    }

    public void getAllUsersSinceLastUpdate(Long localLastUpdate,
                                           GetAllUsersListener listener) {
        this.db.collection(USER_COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(USER_LAST_UPDATE,
                        new Timestamp(localLastUpdate, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<User> users = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = User.fromJson(document.getData());
                            users.add(user);
                        }
                    }
                    listener.onComplete(users);
                });
    }

    public void getUserById(String id, GetMovieItemListener<User> listener) {
        this.db.collection(USER_COLLECTION_NAME)
                .whereEqualTo(FieldPath.documentId(), id)
                .get()
                .addOnCompleteListener(task -> {
                    User user = null;
                    if (task.isSuccessful()) {
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        user = User.fromJson(documentSnapshot.getData());
                    }
                    listener.onComplete(user);
                });
    }

    public void addUser(User user, AddUserListener listener) {
        this.db.collection(USER_COLLECTION_NAME)
                .document(user.getUserId())
                .set(user.toJson())
                .addOnCompleteListener(task -> listener.onComplete());
    }

    public void updateUser(User user, UpdateUserListener listener) {
        this.db.collection(USER_COLLECTION_NAME)
                .document(user.getUserId())
                .set(user.toJson())
                .addOnCompleteListener(task -> listener.onComplete());
    }

    // TODO: Need to implement after implementing working with firebase storage
    public void uploadUserImage(Bitmap imageBmp, String name, UploadUserImageListener listener) {
        /*final StorageReference imagesRef = storage.getReference().child(IMAGE_FOLDER).child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    listener.onComplete(uri.toString());
                }));*/
    }
}
