package com.example.mark.report_card.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.example.mark.report_card.R;
import com.example.mark.report_card.navigation.SelectSemesterActivity;

import data.Semester;
import managers.DBManager;
import managers.SemesterManager;

public class EditSemesterPopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    EditText semesterNameEditText;

    String semesterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_semester_pop_up);

        semesterNameEditText = (EditText) findViewById(R.id.semesterNameEditText);

        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");

        semesterNameEditText.setText(semesterName);

        changeActivitySize();
    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8 * width), (int) (0.5 * height));
    }


    public void editSemesterButtonClicked(View view){
        SemesterManager semesterManager = SemesterManager.getInstance(this);
        DBManager dbManager = DBManager.getInstance(this);

        Semester semester = semesterManager.getSemester(semesterName);
        semester.setName(semesterNameEditText.getText().toString());

        dbManager.updateSemesterName(semesterName, semesterNameEditText.getText().toString());

        Intent intent = new Intent(this, SelectSemesterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }
}
