package com.example.mark.gradetracker;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        SemesterManager semesterManager = SemesterManager.getInstance();
        Semester semester = semesterManager.getSemester(semesterName);

        ListAdapter coursesAdapter = new CourseListAdapter(this, semester.getCourses());
        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);
        coursesListView.setAdapter(coursesAdapter);

        coursesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Course course = (Course) parent.getItemAtPosition(position);
                        goToCourseInfo(course);
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
                R.anim.animation, R.anim.animation2).toBundle();
        startActivity(intent, bndlanimation);
    }

}
