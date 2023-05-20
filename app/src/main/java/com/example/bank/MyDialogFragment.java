package com.example.bank;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import java.util.List;

public class MyDialogFragment extends DialogFragment implements DialogInterface {
Activity activity;
Context context;
    @Override
    public void cancel() {
        getDialog().dismiss();
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof Activity){
            activity = (Activity) context;
            this.context = context;
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog, null);
        builder.setView(view);
        Button buttonCancel= (Button) view.findViewById(R.id.buttonCancel);
        Button buttonEnter= (Button) view.findViewById(R.id.buttonEnter);
        EditText login= (EditText) view.findViewById(R.id.editText_login);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                Log.i("click","clicked");
            }
        });

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Application app = activity.getApplication();
                UsersViewModel mUsersViewModel = new UsersViewModel(app);
                String enteredLogin=login.getText().toString();
                LocalDatabase user = new LocalDatabase(1, enteredLogin,"");
                mUsersViewModel.insert(user);
                cancel();

                Log.i("click","clicked");
            }
        });
        // Остальной код
        return builder.create();
    }

}

