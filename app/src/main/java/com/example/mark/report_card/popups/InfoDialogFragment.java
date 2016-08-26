package com.example.mark.report_card.popups;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark.report_card.R;


public class InfoDialogFragment extends DialogFragment {

    String activity;
    TextView infoText;

    public static InfoDialogFragment newInstance(String activity) {
        InfoDialogFragment f = new InfoDialogFragment();

        // Supply course input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("activity", activity);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (String) getArguments().getSerializable("activity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_info, container, false);
        getDialog().setTitle(getString(R.string.info_dialog_title));

        infoText = (TextView) rootView.findViewById(R.id.infoText);

        if(activity.equals("SelectSemesterActivity")){
            infoText.setText(getString(R.string.info_dialog_semesters));
        } else if (activity.equals("SelectCourseActivity")){
            infoText.setText(getString(R.string.info_dialog_courses));
        } else if (activity.equals("CourseInfoActivity")){
            infoText.setText(getString(R.string.info_dialog_course_info));
        }

        return rootView;
    }
}
