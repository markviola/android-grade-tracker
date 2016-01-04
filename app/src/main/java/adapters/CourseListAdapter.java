package adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mark.gradetracker.R;

import java.util.ArrayList;

import data.Course;

public class CourseListAdapter extends ArrayAdapter<Course>{

    private String TAG = "customFilter";

    public CourseListAdapter(Context context, ArrayList<Course> courses) {
        super(context, R.layout.custom_course_list_row, courses);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseInflater = LayoutInflater.from(getContext()); //Inflater used, to get ready for rending
        View courseView = courseInflater.inflate(R.layout.custom_course_list_row, parent, false);

        Course courseItem = getItem(position);
        TextView courseNameAndCode = (TextView) courseView.findViewById(R.id.courseNameAndCode);
        TextView courseCurrentGrade = (TextView) courseView.findViewById(R.id.courseCurrentGrade);
        TextView inProgressIndicator = (TextView) courseView.findViewById(R.id.inProgressIndicator);

        courseCurrentGrade.setTypeface(null, Typeface.BOLD);
        courseNameAndCode.setText(courseItem.getName() + " (" + courseItem.getCode() + ")");
        courseCurrentGrade.setText("Current Grade: " + courseItem.getCurrentGrade());

        if(courseItem.getInProgress()){
            inProgressIndicator.setTextColor(Color.parseColor("#ffd633"));
            inProgressIndicator.setText("In Progress");
        } else {
            inProgressIndicator.setTextColor(Color.parseColor("#47d147"));
            inProgressIndicator.setText("Finished!");
        }

        return courseView;
    }
}
