package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mark.gradetracker.R;

import java.util.ArrayList;

import data.Course;

public class AddCourseListAdapter extends ArrayAdapter<Course>{

    private String TAG = "customFilter";

    TextView courseName;
    Button deleteButton;

    public AddCourseListAdapter(Context context, ArrayList<Course> courses) {
        super(context, R.layout.custom_add_course_list_row, courses);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseInflater = LayoutInflater.from(getContext()); //Inflater used, to get ready for rending
        View courseView = courseInflater.inflate(R.layout.custom_add_course_list_row, parent, false);

        Course courseItem = getItem(position);
        courseName = (TextView) courseView.findViewById(R.id.courseName);
        deleteButton = (Button) courseView.findViewById(R.id.deleteButton);

        //Allows you to determine which position in the list, the delete button is on by calling
        //getTag() in the corresponding onClick method
        deleteButton.setTag(position);

        courseName.setText(courseItem.getName());

        return courseView;
    }
}