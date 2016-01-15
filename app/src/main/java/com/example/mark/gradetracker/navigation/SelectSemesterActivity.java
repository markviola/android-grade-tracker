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
import com.example.mark.gradetracker.adding.AddSemesterActivity;
import com.example.mark.gradetracker.editting.SettingsActivity;
import com.example.mark.gradetracker.popups.EditMarkOptionsPopUpActivity;
import com.example.mark.gradetracker.popups.EditSemesterOptionsPopUpActivity;

import adapters.SemesterListAdapter;
import data.DBManager;
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

        //Retrieve all data from the database
        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);
        semesterManager.setSemesters(dbManager.getAllSemesters());

        //Set the corresponding list adapter
        ListAdapter semesterAdapter = new SemesterListAdapter(this, semesterManager.getSemesters());
        ListView semesterListView = (ListView) findViewById(R.id.semesterListView);
        semesterListView.setAdapter(semesterAdapter);

        //Executes when the user does a short click on a semester item in the list
        semesterListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Semester semester = (Semester) parent.getItemAtPosition(position);
                        goToCourses(semester);
                    }
                }
        );

        //Executes when the user does a long click on a semester item in the list
        semesterListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Semester semester = (Semester) parent.getItemAtPosition(position);
                        goToEditSemesterOptions(semester.getName());
                        return true;
                    }
                }
        );
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToCourses(Semester semester){
        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", semester.getName());
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.left_to_right_transition, R.anim.left_to_right_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

    private void goToEditSemesterOptions(String semesterName){
        Intent intent = new Intent(this, EditSemesterOptionsPopUpActivity.class);
        intent.putExtra("semesterName", semesterName);
        startActivity(intent);
    }

    public void addSemesterButtonClicked(View view){
        Intent intent = new Intent(this, AddSemesterActivity.class);
        startActivity(intent);
    }

    public void settingsClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.right_to_left_transition, R.anim.right_to_left_transition_2).toBundle();
        startActivity(intent, bndlanimation);
        startActivity(intent);
    }

}
