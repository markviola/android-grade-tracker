package adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mark.report_card.R;

import java.util.ArrayList;

import data.Course;
import managers.SettingsManager;

public class CourseListAdapter extends ArrayAdapter<Course>{

    private String TAG = "customFilter";
    SettingsManager settingsManager;

    public CourseListAdapter(Context context, ArrayList<Course> courses) {
        super(context, R.layout.custom_course_list_row, courses);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseInflater = LayoutInflater.from(getContext()); //Inflater used, to get ready for rending
        View courseView = courseInflater.inflate(R.layout.custom_course_list_row, parent, false);

        settingsManager = SettingsManager.getInstance(getContext());

        Course courseItem = getItem(position);
        TextView courseNameAndCode = (TextView) courseView.findViewById(R.id.courseNameAndCode);
        TextView courseCurrentGrade = (TextView) courseView.findViewById(R.id.courseCurrentGrade);
        TextView inProgressIndicator = (TextView) courseView.findViewById(R.id.inProgressIndicator);

        Double currentGrade = courseItem.getCurrentGrade();

        courseNameAndCode.setTypeface(null, Typeface.BOLD);

        String courseDisplayText = null;
        if(settingsManager.getCourseDisplay().equals("both")){
            courseDisplayText = courseItem.getName() + " (" + courseItem.getCode() + ")";
        } else if (settingsManager.getCourseDisplay().equals("courseName")){
            courseDisplayText = courseItem.getName();
        } else if (settingsManager.getCourseDisplay().equals("courseCode")){
            courseDisplayText = courseItem.getCode();
        } else {
            courseDisplayText ="ERROR";
        }

        // Size the text to fit
        if(courseDisplayText.length() > 32){
            courseNameAndCode.setTextSize(16);
        } else if (courseDisplayText.length() > 25){
            courseNameAndCode.setTextSize(20);
        }
        courseNameAndCode.setText(courseDisplayText);

        if(currentGrade == -1){
            courseCurrentGrade.setText("Current Grade: N/A");
        } else{
            courseCurrentGrade.setText(String.format("Current Grade: %.2f%%", currentGrade));
        }

        if(courseItem.getCurrentTotalWeight() < 100){
            inProgressIndicator.setTextColor(Color.parseColor("#ffd633"));
            inProgressIndicator.setText("In Progress");
        } else {
            inProgressIndicator.setTextColor(Color.parseColor("#47d147"));
            inProgressIndicator.setText("Finished!");
        }

        return courseView;
    }
}
