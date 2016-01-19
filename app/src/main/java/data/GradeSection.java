package data;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GradeSection implements Serializable{

    private String TAG = "customFilter";

    private String _sectionName;
    private double _weight;
    protected ArrayList<Mark> _marks;

    public GradeSection(String sectionName, double weight){
        this._sectionName = sectionName;
        this._weight = weight;
        this._marks = new ArrayList<>();
    }

    public void addMark(Mark mark){
        this._marks.add(mark);
    }

    public void deleteMark(Mark markToDelete){
        for(Mark mark: _marks){
            if(markToDelete.toString().equals(mark.toString())){
                this._marks.remove(mark);
            }
        }
    }

    public ArrayList<Mark> getMarks(){
        return this._marks;
    }

    public int numMarks(){
        return this._marks.size();
    }

    public String getSectionName(){
        return this._sectionName;
    }

    public void setSectionName(String newSectionName){
        this._sectionName = newSectionName;
    }

    public double getWeight(){
        return this._weight;

    }

    public void setWeight(double newWeight){
        this._weight = newWeight;
    }

    public Mark getMarkByName(String markName){
        for(Mark mark: _marks){
            if(mark.getName().equals(markName)){
                return mark;
            }
        }
        return null;
    }

    public String toString(){
        String retString = getSectionName() + "###" + getWeight() + "###" + getNumTopMarks();

        for (Mark grade: _marks){
            retString += "###" + grade;
        }
        retString += "####";

        return retString;
    }

    //public abstract double getWeight();
    public abstract double getSectionGrade();
    public abstract String getNumTopMarks();


}
