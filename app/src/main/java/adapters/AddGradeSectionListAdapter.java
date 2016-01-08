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
import data.GradeSection;
import data.Semester;

public class AddGradeSectionListAdapter extends ArrayAdapter<GradeSection>{

    private String TAG = "customFilter";

    TextView gradeSectionInfo;
    Button deleteButton;

    public AddGradeSectionListAdapter(Context context, ArrayList<GradeSection> gradeSections) {
        super(context, R.layout.custom_add_grade_section_list_row, gradeSections);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseInflater = LayoutInflater.from(getContext()); //Inflater used, to get ready for rending
        View courseView = courseInflater.inflate(R.layout.custom_add_grade_section_list_row, parent, false);

        GradeSection gradeSectionItem = getItem(position);
        gradeSectionInfo = (TextView) courseView.findViewById(R.id.gradeSectionInfo);
        deleteButton = (Button) courseView.findViewById(R.id.deleteButton);

        //Allows you to determine which position in the list, the delete button is on by calling
        //getTag() in the corresponding onClick method
        deleteButton.setTag(position);

        gradeSectionInfo.setText(gradeSectionItem.getSectionName());

        return courseView;
    }

}