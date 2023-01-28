package com.example.movieshare.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class NonExistingMovieDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error");
        builder.setMessage("Adding a user movie comment failed " +
                "because there is no movie with the typed name");
        builder.setPositiveButton("OK", (dialogInterface, i) ->
                Toast.makeText(getContext(),
                                "Failed to add user movie comment",
                                Toast.LENGTH_LONG)
                        .show());
        return builder.create();
    }
}
