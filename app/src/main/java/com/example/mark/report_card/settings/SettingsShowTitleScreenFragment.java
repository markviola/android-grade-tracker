package com.example.mark.report_card.settings;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mark.report_card.R;

public class SettingsShowTitleScreenFragment extends Fragment{

    private String TAG = "customFilter";

    RadioGroup radioGroup;
    RadioButton yesRadioButton;
    RadioButton noRadioButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_show_title_screen, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        yesRadioButton = (RadioButton) view.findViewById(R.id.yesRadioButton);
        noRadioButton = (RadioButton) view.findViewById(R.id.noRadioButton);

        return view;
    }

    public boolean getShowTitleScreen(){
        int selectedID = radioGroup.getCheckedRadioButtonId();
        return selectedID == R.id.yesRadioButton;
    }

    public void setShowTitleScreen(boolean showTitleScreen){
        if(showTitleScreen){
            radioGroup.check(R.id.yesRadioButton);
        } else {
            radioGroup.check(R.id.noRadioButton);
        }
    }

}
