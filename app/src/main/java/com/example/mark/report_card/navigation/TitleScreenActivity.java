package com.example.mark.report_card.navigation;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mark.report_card.R;


import managers.DBManager;
import managers.SemesterManager;
import managers.SettingsManager;

public class TitleScreenActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView welcomeText;
    TextView usernameText;
    TextView CGPAText;
    TextView currentSemesterGrade;

    DBManager dbManager;
    SemesterManager semesterManager;
    SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        dbManager = DBManager.getInstance(this);
        semesterManager = SemesterManager.getInstance(this);
        settingsManager = SettingsManager.getInstance(this);
        semesterManager.setSemesters(dbManager.getAllSemesters());

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        usernameText = (TextView) findViewById(R.id.usernameText);
        CGPAText = (TextView) findViewById(R.id.CGPAText);
        currentSemesterGrade = (TextView) findViewById(R.id.currentSemesterGrade);

        //Change the welcoming text to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        welcomeText.setTypeface(font);
        usernameText.setTypeface(font);

        settingsSetup();

        usernameText.setText(settingsManager.getUsername());
        if(!settingsManager.getShowTitleScreen()){
            goToSelectSemester(false);
        }

        if(semesterManager.getCGPA() != -1){
            CGPAText.setText(String.format("%s %.2f", getString(R.string.title_screen_CPGA_text),
                    semesterManager.getCGPA()));
        } else {
            CGPAText.setText(getString(R.string.title_screen_no_cgpa));
        }

        if(semesterManager.getCurrentSemesterSGPA() != -1){
            currentSemesterGrade.setText(String.format("%s %.2f",
                    getString(R.string.title_screen_current_semester_GPA),
                    semesterManager.getCurrentSemesterSGPA()));
        } else {
            currentSemesterGrade.setText(getString(R.string.title_screen_no_sgpa));
        }
    }

    public void goToSelectSemesterButtonClicked(View view){
        goToSelectSemester(true);
    }


    private void settingsSetup(){
        if (dbManager.settingsTableIsEmpty()){
            dbManager.addSettingsInfo("username", "User");
            dbManager.addSettingsInfo("showTitleScreen", "false");
            dbManager.addSettingsInfo("courseDisplay", "both");
        } else {
            settingsManager.setUsername(dbManager.getSettingState("username"));
            settingsManager.setShowTitleScreen(dbManager.getSettingState("showTitleScreen"));

            //Check if database just updated to version 11
            if(dbManager.getSettingState("courseDisplay").equals("ERROR STATE")){
                dbManager.deleteSettingInfo("courseDisplay");
                dbManager.addSettingsInfo("courseDisplay", "both");
            }

            settingsManager.setCourseDisplay(dbManager.getSettingState("courseDisplay"));
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToSelectSemester(boolean showTransition){
        Intent intent = new Intent(this, SelectSemesterActivity.class);

        if(showTransition){
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                    R.anim.left_to_right_transition, R.anim.left_to_right_transition_2).toBundle();
            startActivity(intent, bndlanimation);
        } else {
            // Prevent the situation where the back button is clicked in SelectSemesterActivity
            // and the animation swipes to itself
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
            startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBackPressed() {
        //Do nothing
    }


}

