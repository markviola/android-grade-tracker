package com.example.mark.gradetracker.navigation;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.popups.EditCourseOptionsPopUpActivity;

import adapters.CourseListAdapter;
import data.Course;
import data.Semester;
import managers.SemesterManager;

public class SelectCourseActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView selectCourseTitle;

    String semesterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_list);

        selectCourseTitle = (TextView) findViewById(R.id.selectCourseTitle);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        selectCourseTitle.setTypeface(font);

        Intent intent = getIntent();

        semesterName = (String) intent.getSerializableExtra("semesterName");
        SemesterManager semesterManager = SemesterManager.getInstance(this);
        Semester semester = semesterManager.getSemester(semesterName);

        ListAdapter coursesAdapter = new CourseListAdapter(this, semester.getCourses());
        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);
        coursesListView.setAdapter(coursesAdapter);

        //Executes when the user does a short click on a course item in the list
        coursesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Course course = (Course) parent.getItemAtPosition(position);
                        goToCourseInfo(course);
                    }
                }
        );

        //Executes when the user does a long click on a course item in the list
        coursesListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Course course = (Course) parent.getItemAtPosition(position);
                        goToCourseOptions(course);
                        return true;
                    }
                }
        );
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToCourseInfo(Course course){
        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", course);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.left_to_right_transition, R.anim.left_to_right_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

    private void goToCourseOptions(Course course){
        Intent intent = new Intent(this, EditCourseOptionsPopUpActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", course);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBackPressed(){
        Intent intent = new Intent(this, SelectSemesterActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.right_to_left_transition, R.anim.right_to_left_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

}
