package com.example.mark.gradetracker.adding;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.popups.AddMarkOptionsPopUpActivity;

import java.util.ArrayList;

import adapters.AddMarkListAdapter;
import data.Course;
import data.GradeSection;
import data.GradeSectionAllMarks;
import data.GradeSectionTopMarks;
import data.Mark;

public class AddGradeSectionActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    EditText gradeSectionNameEditText;
    EditText gradeSectionWeightEditText;
    EditText topMarksEditText;
    CheckBox isTopMarksCheckBox;
    TextView addGradeSectionTitle;
    TextView topMarksText1;
    TextView topMarksText2;

    ArrayList<Course> courses;
    ArrayList<GradeSection> gradeSections;
    ArrayList<Mark> marks;
    String newSemesterName;
    String newCourseName;
    String newCourseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade_section);

        gradeSectionNameEditText = (EditText) findViewById(R.id.gradeSectionNameEditText);
        gradeSectionWeightEditText = (EditText) findViewById(R.id.gradeSectionWeightEditText);
        topMarksEditText = (EditText) findViewById(R.id.topMarksEditText);
        isTopMarksCheckBox = (CheckBox) findViewById(R.id.isTopMarksCheckBox);
        addGradeSectionTitle = (TextView) findViewById(R.id.addGradeSectionTitle);
        topMarksText1 = (TextView) findViewById(R.id.topMarksText1);
        topMarksText2 = (TextView) findViewById(R.id.topMarksText2);

        topMarksEditText.setVisibility(View.GONE);
        topMarksText1.setVisibility(View.GONE);
        topMarksText2.setVisibility(View.GONE);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addGradeSectionTitle.setTypeface(font);

        Intent intent = getIntent();

        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");

        if(intent.getSerializableExtra("marks") == null){
            marks = new ArrayList<>();
        } else {
            marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
        }

        if(intent.getSerializableExtra("gradeSectionName") != null){
            gradeSectionNameEditText.setText((String) intent.getSerializableExtra("gradeSectionName"));
        }

        if(intent.getSerializableExtra("gradeSectionWeight") != null){
            gradeSectionWeightEditText.setText((String) intent.getSerializableExtra("gradeSectionWeight"));
        }

        ListAdapter marksAdapter = new AddMarkListAdapter(this, marks);
        ListView marksListView = (ListView) findViewById(R.id.newMarksList);
        marksListView.setAdapter(marksAdapter);

        marksListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String course = String.valueOf(parent.getItemAtPosition(position));
                    }
                }
        );
    }

    public void addMarkButtonClicked(View view){
        Intent intent = new Intent(this, AddMarkOptionsPopUpActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionNameEditText.getText().toString());
        intent.putExtra("gradeSectionWeight", gradeSectionWeightEditText.getText().toString());
        intent.putExtra("marks", marks);
        startActivity(intent);

    }

    public void addGradeSectionButtonClicked(View view){
        if(gradeSectionNameEditText.getText().toString().equals("")){
            Toast.makeText(this, "No name for this grade section", Toast.LENGTH_LONG).show();
        } else {
            if(isTopMarksCheckBox.isChecked()){
                if(isValidInteger(topMarksEditText.getText().toString())){
                    GradeSection newGradeSection = new GradeSectionTopMarks(
                            gradeSectionNameEditText.getText().toString(),
                            Double.parseDouble(gradeSectionWeightEditText.getText().toString()),
                            Integer.parseInt(topMarksEditText.getText().toString()));

                    for(int i =0; i<marks.size(); i++){
                        newGradeSection.addMark(marks.get(i));
                    }

                    gradeSections.add(newGradeSection);

                    Intent intent = new Intent(this, AddCourseActivity.class);
                    intent.putExtra("newSemesterName", newSemesterName);
                    intent.putExtra("courses", courses);
                    intent.putExtra("newCourseName", newCourseName);
                    intent.putExtra("newCourseCode", newCourseCode);
                    intent.putExtra("gradeSections", gradeSections);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Enter a valid number of grades", Toast.LENGTH_LONG).show();
                }
            } else {
                GradeSection newGradeSection = new GradeSectionAllMarks(
                        gradeSectionNameEditText.getText().toString(),
                        Double.parseDouble(gradeSectionWeightEditText.getText().toString()));
                for(int i =0; i<marks.size(); i++){
                    newGradeSection.addMark(marks.get(i));
                }
                gradeSections.add(newGradeSection);

                Intent intent = new Intent(this, AddCourseActivity.class);
                intent.putExtra("newSemesterName", newSemesterName);
                intent.putExtra("courses", courses);
                intent.putExtra("newCourseName", newCourseName);
                intent.putExtra("newCourseCode", newCourseCode);
                intent.putExtra("gradeSections", gradeSections);
                startActivity(intent);
            }
        }

    }

    /**
     * If the CheckBox is checked then show the corresponding TextView's and EditText. Otherwise
     * hide the widgets from the user.
     * @param view The view of the AddGradeSectionActivity activity
     */
    public void isTopMarksCheckBoxClicked(View view){
        if(isTopMarksCheckBox.isChecked()){
            topMarksEditText.setVisibility(View.VISIBLE);
            topMarksText1.setVisibility(View.VISIBLE);
            topMarksText2.setVisibility(View.VISIBLE);
        } else{
            topMarksEditText.setVisibility(View.GONE);
            topMarksText1.setVisibility(View.GONE);
            topMarksText2.setVisibility(View.GONE);
        }
    }

    public void settingsButtonClicked(View view){

    }

    public void deleteButtonClicked(View view){
        int position = (Integer) view.getTag();
        //Log.i(TAG, "position: " + position);
        deletePopUp(position);
    }

    /**
     * Method that makes a popup to confirm the deletion of a course
     * @param position The position of the course in the ArrayList
     */
    private void deletePopUp(final int position) {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Delete Mark?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMark(position);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        myAlert.show();
    }

    /**
     * Delete the mark from the current ArrayList of newly added marks
     * @param position The position of the mark in the ArrayList
     */
    private void deleteMark(int position){
        marks.remove(position);
        Intent intent = new Intent(this, AddGradeSectionActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionNameEditText.getText().toString());
        intent.putExtra("gradeSectionWeight", gradeSectionWeightEditText.getText().toString());
        intent.putExtra("marks", marks);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation

        startActivity(intent);
    }

    /**
     * Method runs when the user clicks the back button. Prompts the user in whether or not they
     * want to cancel the current 'add semester' session and go to the main menu.
     */
    public void onBackPressed() {
        returnToAddCoursePopUp();
    }

    /**
     * Method that makes a popup to confirm that the user wants to go back to the main menu and
     * lose all information from the new semester
     */
    private void returnToAddCoursePopUp() {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Go back to the 'Add Course' screen? New grade section will not be added")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToAddCourse();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        myAlert.show();
    }

    /**
     * Take the user to the AddSemesterActivity screen
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToAddCourse(){
        Intent intent = new Intent(this, AddCourseActivity.class);

        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.right_to_left_transition, R.anim.right_to_left_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

    private boolean isValidInteger(String str){
        int i;

        try {
            i = Integer.parseInt(str);
        } catch(NumberFormatException nfe){
            return false;
        }

        if(i > 0){
            return true;
        }
        return false;
    }
}
