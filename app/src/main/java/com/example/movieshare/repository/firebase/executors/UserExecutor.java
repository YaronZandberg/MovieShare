package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.UserConstants.*;

import android.graphics.Bitmap;

import com.example.movieshare.listeners.authentication.*;
import com.example.movieshare.listeners.movies.GetMovieItemListener;
import com.example.movieshare.repository.models.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UserExecutor {
    private static final UserExecutor userExecutorInstance = new UserExecutor();
    public static final String IMAGE_FOLDER = "users";
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;

    private UserExecutor() {
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    public static UserExecutor instance() {
        return userExecutorInstance;
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

    public void uploadUserImage(Bitmap imageBmp, String name, UploadUserImageListener listener) {
        final StorageReference imagesRef = this.storage.getReference().child(IMAGE_FOLDER).child(name);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl()
                        .addOnFailureListener(uri -> listener.onComplete(null))
                        .addOnSuccessListener(uri -> listener.onComplete(uri.toString())));
    }
}
