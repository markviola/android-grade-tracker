package com.example.mark.report_card.settings;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mark.report_card.R;

public class SettingsUsernameFragment extends Fragment {

    private String TAG = "customFilter";

    EditText usernameEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_username, container, false);

        usernameEditText = (EditText) view.findViewById(R.id.usernameEditText);

        return view;
    }

    public String getNewUsername(){
        return usernameEditText.getText().toString();
    }

    public void setUsername(String username){
        usernameEditText.setText(username);
    }
}
