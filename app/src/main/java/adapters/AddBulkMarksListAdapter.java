package adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mark.report_card.R;

import java.util.ArrayList;
import java.util.HashMap;

import data.Mark;

public class AddBulkMarksListAdapter extends ArrayAdapter<Mark>{

    private String TAG = "customFilter";

    ViewHolder holder;
    private Context context;
    ArrayList<Mark> newMarks;

    static class ViewHolder{
        TextView newMarkName;
        EditText newMarkGrade;
        Button deleteButton;
    }

    HashMap<Integer, Double> values = new HashMap<>();

    public AddBulkMarksListAdapter(Context context, ArrayList<Mark> newMarks) {
        super(context, R.layout.custom_add_bulk_marks_list_row, newMarks);
        this.context = context;
        this.newMarks = newMarks;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View marksView = convertView;

        if(marksView == null){
            LayoutInflater courseInflater = LayoutInflater.from(getContext()); //Inflater used, to get ready for rending
            marksView = courseInflater.inflate(R.layout.custom_add_bulk_marks_list_row, parent, false);

            holder = new ViewHolder();
            holder.newMarkName = (TextView) marksView.findViewById(R.id.newMarkName);
            holder.newMarkGrade = (EditText) marksView.findViewById(R.id.newMarkGrade);
            holder.deleteButton = (Button) marksView.findViewById(R.id.deleteButton);

            //Allows you to determine which position in the list, the delete button is on by calling
            //getTag() in the corresponding onClick method
            holder.deleteButton.setTag(position);

            marksView.setTag(holder);
        } else {
            holder = (ViewHolder) marksView.getTag();
        }

        Mark newMarkItem = newMarks.get(position);

        holder.newMarkName.setText(newMarkItem.getName());
        if(newMarks.get(position).getMark() != null){
            holder.newMarkGrade.setText(newMarks.get(position).getMark().toString());
        }

        holder.newMarkGrade.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    newMarks.get(position).setMark(null);
                }else{
                    newMarks.get(position).setMark(Double.parseDouble(s.toString()));
                }
            }
        });

        return marksView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 500;
    }
}