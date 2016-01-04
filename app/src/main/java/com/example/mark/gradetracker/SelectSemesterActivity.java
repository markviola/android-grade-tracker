package com.example.mark.gradetracker;

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

import adapters.SemesterListAdapter;
import data.Semester;
import managers.SemesterManager;

public class SelectSemesterActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView selectSemesterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_semester_list);

        selectSemesterTitle = (TextView) findViewById(R.id.selectSemesterTitle);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        selectSemesterTitle.setTypeface(font);

        SemesterManager semesterManager = SemesterManager.getInstance();

        ListAdapter semesterAdapter = new SemesterListAdapter(this, semesterManager.getSemesters());
        ListView coursesListView = (ListView) findViewById(R.id.semesterListView);
        coursesListView.setAdapter(semesterAdapter);

        coursesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Semester semester = (Semester) parent.getItemAtPosition(position);
                        goToCourses(semester);
                    }
                }
        );
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToCourses(Semester semester){
        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", semester.getName());
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.animation, R.anim.animation2).toBundle();
        startActivity(intent, bndlanimation);

    }

    public void settingsClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

}
