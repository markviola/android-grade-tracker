package com.example.mark.report_card.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.mark.report_card.R;

public class EditSemesterOptionsPopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView semesterTitleText;

    String semesterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_semester_options_pop_up);

        semesterTitleText = (TextView) findViewById(R.id.semesterTitleText);

        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");

        semesterTitleText.setText(String.format("\"%s\"\nSemester", semesterName));

        changeActivitySize();
    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.7 * width), (int) (0.35 * height));
    }

    public void editSemesterButtonClicked(View view){

    }

    public void deleteSemesterButtonClicked(View view){
        Intent intent = new Intent(this, CustomAlertPopUp.class);
        intent.putExtra("previousActivity", "EditSemesterOptionsPopUpActivity");
        intent.putExtra("semesterName", semesterName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);

//        DBManager dbManager = DBManager.getInstance(this);
//        dbManager.deleteSemester(semesterName);
//
//        Intent intent = new Intent(this, SelectSemesterActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
//        startActivity(intent);
    }

    public void cancelButtonClicked(View view){
        this.finish();
    }
}
