package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mark.report_card.R;

import java.util.ArrayList;

import data.Mark;

public class AddMarkListAdapter extends ArrayAdapter<Mark>{

    private String TAG = "customFilter";

    TextView gradeName;
    Button deleteButton;

    public AddMarkListAdapter(Context context, ArrayList<Mark> marks) {
        super(context, R.layout.custom_add_grade_list_row, marks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater courseInflater = LayoutInflater.from(getContext()); //Inflater used, to get ready for rending
        View courseView = courseInflater.inflate(R.layout.custom_add_grade_list_row, parent, false);

        Mark markItem = getItem(position);
        gradeName = (TextView) courseView.findViewById(R.id.gradeName);
        deleteButton = (Button) courseView.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);

        //Allows you to determine which position in the list, the delete button is on by calling
        //getTag() in the corresponding onClick method
        deleteButton.setTag(position);

        String displayMarkText;
        if(markItem.getMark() == null){
            displayMarkText = "N/A";
        }else {
            displayMarkText = markItem.getMark().toString() + "%";
        }
        gradeName.setText(markItem.getName() + " | Grade: " + displayMarkText);

        return courseView;
    }
}