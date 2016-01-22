package com.example.mark.report_card.navigation;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
            CGPAText.setText(String.format("CGPA: %.2f", semesterManager.getCGPA()));
        } else {
            CGPAText.setText("CGPA: N/A");
        }

        if(semesterManager.getCurrentSemesterSGPA() != -1){
            currentSemesterGrade.setText(String.format("Current Semester: %.2f", semesterManager.getCurrentSemesterSGPA()));
        } else {
            currentSemesterGrade.setText("Current Semester: N/A");
        }
    }

    public void goToSelectSemesterButtonClicked(View view){
        goToSelectSemester(true);
    }

    private void settingsSetup(){
        if (dbManager.settingsTableIsEmpty()){
            dbManager.addSettingsInfo("username", "User");
            dbManager.addSettingsInfo("showTitleScreen", "false");
        } else {
            settingsManager.setUsername(dbManager.getSettingState("username"));
            settingsManager.setShowTitleScreen(dbManager.getSettingState("showTitleScreen"));
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

