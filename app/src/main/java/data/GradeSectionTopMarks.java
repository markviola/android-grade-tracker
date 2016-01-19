package data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Object containing all the marks of a grade section (i.e assignments, tests, quizzes).
 * This GradeSection will take into account only a specific amount of the user's top marks when
 * calculating the grade section mark
 */
public class GradeSectionTopMarks extends GradeSection {

    private String TAG = "customFilter";

    private int _numMarks;

    public GradeSectionTopMarks(String sectionName, double weight, int numMarks){
        super(sectionName, weight);
        this._numMarks = numMarks;
    }

    public String getNumTopMarks(){
        return String.valueOf(this._numMarks);
    }

    @Override
    public double getSectionGrade() {
        ArrayList<Mark> validMarks = getValidMarks();
        validMarks = sortValidMarks(validMarks);

        double marksTotal = 0.0;

        if(validMarks.size() > 0){
            for(int i=0; i<Math.min(validMarks.size(), _numMarks); i++){
                marksTotal += validMarks.get(i).getMark();
            }
            return marksTotal/Math.min(validMarks.size(), _numMarks);
        } else {
            return -1;
        }

    }

    private ArrayList<Mark> getValidMarks(){
        ArrayList<Mark> validMarks = new ArrayList<>();

        for(Mark mark: _marks){
            if(mark.getMark() != null){
                validMarks.add(mark);
            }
        }

        return validMarks;
    }

    private ArrayList<Mark> sortValidMarks(ArrayList<Mark> validMarks){
        for(int i=0; i<validMarks.size(); i++){
            Mark tempMark = validMarks.get(i);
            for(int j=i-1; j>=0 && tempMark.getMark() > validMarks.get(j).getMark(); j--){
                Collections.swap(validMarks, j, j+1);
            }
        }

        return validMarks;
    }
}
