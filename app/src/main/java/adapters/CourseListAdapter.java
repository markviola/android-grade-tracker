package adapters;

import android.content.Context;
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
        TextView courseCode = (TextView) courseView.findViewById(R.id.courseCode);
        TextView currentGrade = (TextView) courseView.findViewById(R.id.currentGrade);

        courseCode.setText(courseItem.getName());
        currentGrade.setText(" | Current Grade: " + "00.00");

        return courseView;
    }
}
