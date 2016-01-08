package adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.mark.gradetracker.R;

import java.util.HashMap;
import java.util.List;

import data.Course;
import data.GradeSection;
import data.Mark;


public class GradeSectionInfoListAdapter extends BaseExpandableListAdapter{

    private String TAG = "customFilter";

    private List<GradeSection> headerTitles;
    private HashMap<String,List<Mark>> childTitles;
    private Context context;

    public GradeSectionInfoListAdapter(Context context, List<GradeSection> headerTitles, HashMap<String,List<Mark>> childTitles){
        this.context = context;
        this.headerTitles = headerTitles;
        this.childTitles = childTitles;
    }

    @Override
    public int getGroupCount() {
        return headerTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childTitles.get(headerTitles.get(groupPosition).getSectionName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childTitles.get(headerTitles.get(groupPosition).getSectionName()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GradeSection gradeSection = (GradeSection) this.getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_course_info_list_row_heading, null);
        }
        TextView gradeSectionName = (TextView) convertView.findViewById(R.id.gradeSectionName);
        TextView currentGradeText = (TextView) convertView.findViewById(R.id.currentGradeText);

        gradeSectionName.setTypeface(null, Typeface.BOLD);
        gradeSectionName.setText(gradeSection.getSectionName());

        currentGradeText.setTypeface(null, Typeface.BOLD);
        currentGradeText.setText(String.format("%.2f%%", gradeSection.getSectionGrade()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Mark mark = (Mark) this.getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_course_info_list_row_child, null);
        }

        TextView markName = (TextView) convertView.findViewById(R.id.markName);
        TextView currentGradeText = (TextView) convertView.findViewById(R.id.currentGradeText);

        markName.setText(mark.getName());
        if(mark.getMark() != null){
            currentGradeText.setText(String.format("%.2f%%",mark.getMark()));
        } else {
            currentGradeText.setText("N/A");
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

