package com.example.mark.report_card.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mark.report_card.R;


public class SettingsCourseDisplayFragment extends Fragment {

    private String TAG = "customFilter";

    RadioGroup radioGroup;
    RadioButton courseNameRadioButton;
    RadioButton courseCodeRadioButton;
    RadioButton bothRadioButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_course_display, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        courseNameRadioButton = (RadioButton) view.findViewById(R.id.courseNameRadioButton);
        courseCodeRadioButton = (RadioButton) view.findViewById(R.id.courseCodeRadioButton);
        bothRadioButton = (RadioButton) view.findViewById(R.id.bothRadioButton);

        return view;
    }

    public String getCourseDisplay(){
        int selectedID = radioGroup.getCheckedRadioButtonId();
        if (selectedID == R.id.courseNameRadioButton){
            return "courseName";
        } else if (selectedID == R.id.courseCodeRadioButton){
            return "courseCode";
        } else {
            return "both";
        }
    }

    public void setCourseDisplay(String courseDisplay){
        if(courseDisplay.equals("courseName")){
            radioGroup.check(R.id.courseNameRadioButton);
        } else if (courseDisplay.equals("courseCode")){
            radioGroup.check(R.id.courseCodeRadioButton);
        } else {
            radioGroup.check(R.id.bothRadioButton);
        }
    }
}
