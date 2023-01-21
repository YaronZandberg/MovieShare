package com.example.movieshare.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class UpdateUserMovieCommentDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update user movie comment");
        builder.setMessage("Updating user movie comment operation was completed successfully");
        builder.setPositiveButton("OK", (dialogInterface, i) ->
                Toast.makeText(getContext(),
                                "User movie comment has been updated",
                                Toast.LENGTH_LONG)
                        .show());
        return builder.create();
    }
}
