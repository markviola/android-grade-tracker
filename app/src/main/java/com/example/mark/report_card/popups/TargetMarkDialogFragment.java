package com.example.mark.report_card.popups;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mark.report_card.R;

import data.Course;


public class TargetMarkDialogFragment extends DialogFragment{

    Course course;

    TextView currentMarkText;
    TextView currentWeightText;
    TextView targetMarkText;
    TextView targetMarkPercentText;
    TextView resultText;
    EditText targetMarkEditText;
    Button calculateTargetMarkButton;

    public static TargetMarkDialogFragment newInstance(Course course) {
        TargetMarkDialogFragment f = new TargetMarkDialogFragment();

        // Supply course input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("course", course);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = (Course) getArguments().getSerializable("course");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_target_mark, container, false);

        currentMarkText = (TextView) rootView.findViewById(R.id.currentMarkText);
        currentWeightText = (TextView) rootView.findViewById(R.id.currentWeightText);
        targetMarkText = (TextView) rootView.findViewById(R.id.targetMarkText);
        targetMarkPercentText = (TextView) rootView.findViewById(R.id.targetMarkPercentText);
        resultText = (TextView) rootView.findViewById(R.id.resultText);
        targetMarkEditText = (EditText) rootView.findViewById(R.id.targetMarkEditText);
        calculateTargetMarkButton = (Button) rootView.findViewById(R.id.calculateTargetMarkButton);

        resultText.setVisibility(View.GONE);

        currentMarkText.setText(String.format("Current mark: %.2f%%", course.getCurrentGrade()));
        currentWeightText.setText(String.format("Current weight: %.2f%%", course.getCurrentTotalWeight()));

        getDialog().setTitle("Target mark");

        calculateTargetMarkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentMarkText.setVisibility(View.GONE);
                currentWeightText.setVisibility(View.GONE);
                targetMarkEditText.setVisibility(View.GONE);
                targetMarkText.setVisibility(View.GONE);
                targetMarkPercentText.setVisibility(View.GONE);
                calculateTargetMarkButton.setVisibility(View.GONE);

                double targetMark = Double.parseDouble(targetMarkEditText.getText().toString());
                double neededMark = 100*(targetMark - (course.getCurrentTotalWeight()/100 * course.getCurrentGrade()))/
                        (100 - course.getCurrentTotalWeight());

                resultText.setText(String.format("To get a final mark of %.2f%%, you need to get " +
                        "%.2f%% on the remaining %.2f%% of the course", targetMark, neededMark,
                        100 - course.getCurrentTotalWeight()));
                resultText.setVisibility(View.VISIBLE);
            }

        });

        return rootView;
    }

}
