package com.example.carbuddy.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.carbuddy.R;
import com.example.carbuddy.controllers.fragment_carInfo;
import com.example.carbuddy.listeners.DeleteDialogListener;
import com.example.carbuddy.singletons.CarSingleton;

public class DeleteConfirmationDialogFragment extends DialogFragment {

    private DeleteDialogListener deleteListener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Delete?")
                .setMessage(getString(R.string.delete_confirmation))
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteListener.onDeleteYes(id);
                    }
                })
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteListener.onDeleteYes(id);
                    }
                })
                .create();
    }

    public static String TAG = "DeleteConfirmationDialog";

    public void setDeleteYesListener(fragment_carInfo fragment){
        this.deleteListener = fragment;
    }
}
