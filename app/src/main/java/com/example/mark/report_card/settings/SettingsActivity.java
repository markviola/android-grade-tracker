package com.example.mark.report_card.settings;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mark.report_card.R;
import com.example.mark.report_card.adding.AddCourseActivity;
import com.example.mark.report_card.adding.AddGradeSectionActivity;
import com.example.mark.report_card.adding.AddSemesterActivity;
import com.example.mark.report_card.navigation.CourseInfoActivity;
import com.example.mark.report_card.navigation.SelectCourseActivity;
import com.example.mark.report_card.navigation.SelectSemesterActivity;

import java.util.ArrayList;

import data.Course;
import data.GradeSection;
import data.Mark;
import managers.SettingsManager;

import static com.example.mark.report_card.R.*;

public class SettingsActivity extends AppCompatActivity{

    private String TAG = "customFilter";

    SettingsManager settingsManager;

    TextView settingsTitleText;

    SettingsUsernameFragment settingsUsernameFragment;
    SettingsShowTitleScreenFragment settingsShowTitleScreenFragment;
    String previousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_settings);

        settingsManager = SettingsManager.getInstance(this);

        settingsUsernameFragment = (SettingsUsernameFragment) getSupportFragmentManager().findFragmentById(id.settingsUsernameFragment);
        settingsShowTitleScreenFragment = (SettingsShowTitleScreenFragment) getSupportFragmentManager().findFragmentById(id.settingsShowTitleScreenFragment);
        settingsTitleText = (TextView) findViewById(R.id.settingsTitleText);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        settingsTitleText.setTypeface(font);

        Intent intent = getIntent();
        previousActivity = (String) intent.getSerializableExtra("previousActivity");

        setCurrentSettings();
    }

    public void saveAndContinueButtonClicked(View view) {
        updateUsername();
        updateShowTitleScreen();

        switch (previousActivity) {
            case "SelectSemesterActivity":
                goToSelectSemesterActivity();
                break;
            case "SelectCourseActivity":
                goToSelectCourseActivity();
                break;
            case "CourseInfoActivity":
                goToCourseInfoActivity();
                break;
            case "AddSemesterActivity":
                goToAddSemesterActivity();
                break;
            case "AddCourseActivity":
                goToAddCourseActivity();
                break;
            case "AddGradeSectionActivity":
                goToAddGradeSectionActivity();
                break;
        }
    }

    private void setCurrentSettings(){
        settingsUsernameFragment.setUsername(settingsManager.getUsername());
        settingsShowTitleScreenFragment.setShowTitleScreen(settingsManager.getShowTitleScreen());
    }

    /*
     * Functions that lead user back to previous activity
     */

    private void goToSelectSemesterActivity(){
        Intent intent = new Intent(this, SelectSemesterActivity.class);
        startActivity(intent);
    }

    private void goToSelectCourseActivity(){
        Intent prevActivity = getIntent();
        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", (String) prevActivity.getSerializableExtra("semesterName"));
        startActivity(intent);
    }

    private void goToCourseInfoActivity(){
        Intent prevActivity = getIntent();
        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", (String) prevActivity.getSerializableExtra("semesterName"));
        intent.putExtra("selectedCourse", (Course) prevActivity.getSerializableExtra("selectedCourse"));
        startActivity(intent);
    }

    private void goToAddSemesterActivity(){
        Intent prevActivity = getIntent();
        Intent intent = new Intent(this, AddSemesterActivity.class);
        intent.putExtra("newSemesterName", (String) prevActivity.getSerializableExtra("newSemesterName"));
        intent.putExtra("courses", (ArrayList<Course>) prevActivity.getSerializableExtra("courses"));
        startActivity(intent);
    }

    private void goToAddCourseActivity(){
        Intent prevActivity = getIntent();
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra("newSemesterName", (String) prevActivity.getSerializableExtra("newSemesterName"));
        intent.putExtra("newCourseName", (String) prevActivity.getSerializableExtra("newCourseName"));
        intent.putExtra("newCourseCode", (String) prevActivity.getSerializableExtra("newCourseCode"));
        intent.putExtra("courses", (ArrayList<Course>) prevActivity.getSerializableExtra("courses"));
        intent.putExtra("gradeSections", (ArrayList<GradeSection>) prevActivity.getSerializableExtra("gradeSections"));
        intent.putExtra("fromSelectCourseActivity", (boolean) prevActivity.getSerializableExtra("fromSelectCourseActivity"));
        startActivity(intent);
    }

    private void goToAddGradeSectionActivity(){
        Intent prevActivity = getIntent();
        Intent intent = new Intent(this, AddGradeSectionActivity.class);
        intent.putExtra("newSemesterName", (String) prevActivity.getSerializableExtra("newSemesterName"));
        intent.putExtra("newCourseName", (String) prevActivity.getSerializableExtra("newCourseName"));
        intent.putExtra("newCourseCode", (String) prevActivity.getSerializableExtra("newCourseCode"));
        intent.putExtra("gradeSectionName", (String) prevActivity.getSerializableExtra("gradeSectionName"));
        intent.putExtra("gradeSectionWeight", (String) prevActivity.getSerializableExtra("gradeSectionWeight"));
        intent.putExtra("courses", (ArrayList<Course>) prevActivity.getSerializableExtra("courses"));
        intent.putExtra("gradeSections", (ArrayList<GradeSection>) prevActivity.getSerializableExtra("gradeSections"));
        intent.putExtra("marks", (ArrayList<Mark>) prevActivity.getSerializableExtra("marks"));
        intent.putExtra("fromSelectCourseActivity", (boolean) prevActivity.getSerializableExtra("fromSelectCourseActivity"));
        intent.putExtra("fromCourseInfoActivity", (boolean) prevActivity.getSerializableExtra("fromCourseInfoActivity"));
        intent.putExtra("selectedCourse" , (Course) prevActivity.getSerializableExtra("selectedCourse"));
        startActivity(intent);
    }

    /*
     * Settings fragment function calls
     */

    public void updateUsername(){
        String newUsername = settingsUsernameFragment.getNewUsername();
        settingsManager.setUsername(newUsername);
    }

    public void updateShowTitleScreen(){
        boolean showTitleScreen = settingsShowTitleScreenFragment.getShowTitleScreen();
        if(showTitleScreen){
            settingsManager.setShowTitleScreen("true");
        } else {
            settingsManager.setShowTitleScreen("false");
        }
    }

}
