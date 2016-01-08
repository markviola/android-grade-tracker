package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mark.gradetracker.R;

import java.util.ArrayList;

import data.Semester;

public class SemesterListAdapter extends ArrayAdapter<Semester>{

    public SemesterListAdapter(Context context, ArrayList<Semester> semesters) {
        super(context, R.layout.custom_semester_list_row, semesters);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseInflater = LayoutInflater.from(getContext()); //Inflater used, to get ready for rending
        View courseView = courseInflater.inflate(R.layout.custom_semester_list_row, parent, false);

        Semester semesterItem = getItem(position);
        TextView semesterName = (TextView) courseView.findViewById(R.id.semesterName);
        TextView numCourses = (TextView) courseView.findViewById(R.id.numCourses);
        TextView SessionalGPA = (TextView) courseView.findViewById(R.id.SessionalGPA);

        semesterName.setText(semesterItem.getName());
        numCourses.setText("|  " + String.valueOf(semesterItem.numCourses()) + " Courses  |");
        SessionalGPA.setText(String.format("SGPA: %.2f", semesterItem.getSGPA()));

        return courseView;
    }
}