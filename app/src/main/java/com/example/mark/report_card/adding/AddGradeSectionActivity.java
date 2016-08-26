package com.example.mark.report_card.adding;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark.report_card.R;
import com.example.mark.report_card.navigation.CourseInfoActivity;
import com.example.mark.report_card.popups.AddMarkOptionsPopUpActivity;
import com.example.mark.report_card.popups.CustomAlertPopUp;
import com.example.mark.report_card.settings.SettingsActivity;

import java.util.ArrayList;

import adapters.AddMarkListAdapter;
import data.Course;
import managers.DBManager;
import data.GradeSection;
import data.GradeSectionAllMarks;
import data.GradeSectionTopMarks;
import data.Mark;
import data.Semester;
import managers.SemesterManager;

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
    boolean fromSelectCourseActivity;
    boolean fromCourseInfoActivity;
    Course selectedCourse;

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

        if(intent.getSerializableExtra("fromCourseInfoActivity") != null &&
                (boolean) intent.getSerializableExtra("fromCourseInfoActivity")){
            newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
            selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
            fromSelectCourseActivity = false;
            fromCourseInfoActivity = true;
        } else {
            newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
            newCourseName = (String) intent.getSerializableExtra("newCourseName");
            newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
            courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
            gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
            fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
            fromCourseInfoActivity = false;
        }

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
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);
        intent.putExtra("marks", marks);
        startActivity(intent);

    }

    public void addGradeSectionButtonClicked(View view){
        if(gradeSectionNameEditText.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.add_grade_section_no_name_inputted), Toast.LENGTH_LONG).show();
        } else if (gradeSectionWeightEditText.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.add_grade_section_no_weight_inputted), Toast.LENGTH_LONG).show();
        } else {
            if(validWeight()){
                if(isTopMarksCheckBox.isChecked()){
                    if(isValidInteger(topMarksEditText.getText().toString())){
                        GradeSection newGradeSection = new GradeSectionTopMarks(
                                gradeSectionNameEditText.getText().toString(),
                                Double.parseDouble(gradeSectionWeightEditText.getText().toString()),
                                Integer.parseInt(topMarksEditText.getText().toString()));

                        for(int i =0; i<marks.size(); i++){
                            newGradeSection.addMark(marks.get(i));
                        }

                        if (fromCourseInfoActivity){
                            goToCourseInfo(newGradeSection);
                        } else {
                            gradeSections.add(newGradeSection);
                            goToAddCourse(false);
                        }

                    } else {
                        Toast.makeText(this, getString(R.string.add_grade_section_not_valid_mark_num), Toast.LENGTH_LONG).show();
                    }
                } else {
                    GradeSection newGradeSection = new GradeSectionAllMarks(
                            gradeSectionNameEditText.getText().toString(),
                            Double.parseDouble(gradeSectionWeightEditText.getText().toString()));
                    for(int i =0; i<marks.size(); i++){
                        newGradeSection.addMark(marks.get(i));
                    }

                    if(fromCourseInfoActivity){
                        goToCourseInfo(newGradeSection);
                    } else {
                        gradeSections.add(newGradeSection);
                        goToAddCourse(false);
                    }
                }
            }
        }

    }

    public void backButtonClicked(View view){
        onBackPressed();
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
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("previousActivity", "AddGradeSectionActivity");
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionNameEditText.getText().toString());
        intent.putExtra("gradeSectionWeight", gradeSectionWeightEditText.getText().toString());
        intent.putExtra("marks", marks);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);
        startActivity(intent);
    }

    public void deleteButtonClicked(View view){
        int position = (Integer) view.getTag();
        deletePopUp(position);
    }

    /**
     * Method that makes a popup to confirm the deletion of a course
     * @param position The position of the course in the ArrayList
     */
    private void deletePopUp(final int position) {
        Intent intent = new Intent(this, CustomAlertPopUp.class);
        intent.putExtra("previousActivity", "AddGradeSectionActivityDeleteMark");
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionNameEditText.getText().toString());
        intent.putExtra("gradeSectionWeight", gradeSectionWeightEditText.getText().toString());
        intent.putExtra("marks", marks);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);
        intent.putExtra("position", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    /**
     * Method runs when the user clicks the back button. Prompts the user in whether or not they
     * want to cancel the current 'add semester' session and go to the main menu.
     */
    public void onBackPressed() {
        returnToActivityPopUp();
    }

    /**
     * Method that makes a popup to confirm that the user wants to go back to the main menu and
     * lose all information from the new semester
     */
    private void returnToActivityPopUp() {

        if (fromCourseInfoActivity){
            Intent intent = new Intent(this, CustomAlertPopUp.class);
            intent.putExtra("previousActivity", "AddGradeSectionActivityBackToCourseInfo");
            intent.putExtra("semesterName", newSemesterName);
            intent.putExtra("selectedCourse", selectedCourse);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CustomAlertPopUp.class);
            intent.putExtra("previousActivity", "AddGradeSectionActivityBackToAddCourse");
            intent.putExtra("newSemesterName", newSemesterName);
            intent.putExtra("newCourseName", newCourseName);
            intent.putExtra("newCourseCode", newCourseCode);
            intent.putExtra("courses", courses);
            intent.putExtra("gradeSections", gradeSections);
            intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
            startActivity(intent);
        }
    }

    /**
     * Take the user to the AddSemesterActivity screen
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToAddCourse(boolean showTransitionAnimation){
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        startActivity(intent);
    }

    private void goToCourseInfo(GradeSection newGradeSection){
        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);

        Semester semester = semesterManager.getSemester(newSemesterName);
        Course course  = semester.getCourseByName(selectedCourse.getName());
        course.addGradeSection(newGradeSection);

        dbManager.updateSemesterInfo(newSemesterName, semester.getCoursesStr());

        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", newSemesterName);
        intent.putExtra("selectedCourse", course);
        startActivity(intent);
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

    private boolean validWeight(){
        if(fromCourseInfoActivity){
            if(selectedCourse.getAllTotalWeight() +
                    Double.parseDouble(gradeSectionWeightEditText.getText().toString()) <= 100){
                return true;
            } else {
                Toast.makeText(this,
                        String.format("%s %.2f%%. %s",
                                getString(R.string.add_grade_section_high_marks_text_1),
                                    selectedCourse.getAllTotalWeight(),
                                    getString(R.string.add_grade_section_high_marks_text_2)),
                                Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            double allTotalWeight = 0;
            for(GradeSection gradeSection: gradeSections){
                allTotalWeight += gradeSection.getWeight();
            }
            if(allTotalWeight +
                    Double.parseDouble(gradeSectionWeightEditText.getText().toString()) <= 100){
                return true;
            } else {
                Toast.makeText(this,
                        String.format("%s %.2f%%. %s",
                                getString(R.string.add_grade_section_high_marks_text_1),
                                    selectedCourse.getAllTotalWeight(),
                                    getString(R.string.add_grade_section_high_marks_text_2)),
                                Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}
