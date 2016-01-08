package com.example.mark.gradetracker.navigation;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.popups.EditGradeSectionOptionsActivity;
import com.example.mark.gradetracker.popups.EditMarkOptionsPopUpActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.GradeSectionInfoListAdapter;
import data.Course;
import data.GradeSection;
import data.Mark;

public class CourseInfoActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    String semesterName;
    Course selectedCourse;

    //Declare relevant activity widgets
    TextView courseNameText;
    TextView courseCodeText;
    TextView currentGrade;
    ExpandableListView gradeSectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        //Initialize relevant activity widgets
        courseNameText = (TextView) findViewById(R.id.courseNameText);
        courseCodeText = (TextView) findViewById(R.id.courseCodeText);
        currentGrade = (TextView) findViewById(R.id.currentGrade);
        gradeSectionList = (ExpandableListView) findViewById(R.id.gradeSectionList);

        //Retrieve data from previous activity
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");

        //Set static text in activity
        courseNameText.setTypeface(null, Typeface.BOLD);
        currentGrade.setTypeface(null, Typeface.BOLD);
        courseNameText.setText(selectedCourse.getName());
        courseCodeText.setText("Course Code: " + selectedCourse.getCode());
        if(selectedCourse.getCurrentGrade() == -1){//Change -1 into a constant and put in  Constants.java
            currentGrade.setText("Current Grade: N/A");
        } else{
            currentGrade.setText(String.format("Current Grade: %.2f%%", selectedCourse.getCurrentGrade()));
        }

        //Generate a HashMap for the ExpandableListView
        HashMap<String, List<Mark>> childList = new HashMap<>();
        for(GradeSection gradeSection: selectedCourse.getGrade()){
            childList.put(gradeSection.getSectionName(), gradeSection.getMarks());
        }

        GradeSectionInfoListAdapter listAdapter = new GradeSectionInfoListAdapter(this, selectedCourse.getGrade(), childList);
        gradeSectionList.setAdapter(listAdapter);

        //Set click listener for when there is a long click
        gradeSectionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                long packedPosition = gradeSectionList.getExpandableListPosition(position);

                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

                //If group item clicked
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    onGroupLongClick(groupPosition);
                }

                //If child item clicked
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    onChildLongClick(groupPosition, childPosition);
                }

                return false;
            }
        });

    }

    public void settingsButtonClicked(View view){

    }

    /**
     * Take the user to the grade section edit activity for editing the grade section clicked
     * @param groupPosition The position of the grade section in the expandableListView
     */
    public void onGroupLongClick(int groupPosition){
        Intent intent = new Intent(this, EditGradeSectionOptionsActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("currentCourse", selectedCourse);
        intent.putExtra("selectedGradeSection", selectedCourse.getGrade().get(groupPosition));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    /**
     * Take the user to the mark edit activity for editing the mark clicked
     * @param groupPosition The position of the grade section in the expandableListView
     * @param childPosition The position of the mark in the expandableListView
     */
    public void onChildLongClick(int groupPosition, int childPosition){
        Intent intent = new Intent(this, EditMarkOptionsPopUpActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("currentCourse", selectedCourse);
        intent.putExtra("selectedGradeSection", selectedCourse.getGrade().get(groupPosition));
        intent.putExtra("selectedMark", selectedCourse.getGrade().get(groupPosition).getMarks().get(childPosition));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBackPressed(){
        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", semesterName);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.right_to_left_transition, R.anim.right_to_left_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

}
