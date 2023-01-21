package com.example.movieshare.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddUserMovieCommentDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add user movie comment");
        builder.setMessage("Adding user movie comment operation was completed successfully");
        builder.setPositiveButton("OK", (dialogInterface, i) ->
                Toast.makeText(getContext(),
                                "User movie comment has been added",
                                Toast.LENGTH_LONG)
                        .show());
        return builder.create();
    }
}
