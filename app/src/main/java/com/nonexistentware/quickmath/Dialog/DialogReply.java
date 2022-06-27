package com.nonexistentware.quickmath.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.nonexistentware.quickmath.R;

public class DialogReply extends AppCompatDialogFragment {
    private TextView attemptCounterTxt;
    int attempts = 3;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog_classicgame, null);

        attemptCounterTxt = view.findViewById(R.id.attempts_counter);
        builder.setView(view).setTitle("Your score is too low. Do you wont to reply?");
        builder.setView(view).setTitle(attempts + " attempts left")
                .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        attempts--;


                    }
                })
                .setNegativeButton("Finish the game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return super.onCreateDialog(savedInstanceState);
    }
}
