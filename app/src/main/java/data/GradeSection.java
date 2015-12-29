package data;


import java.io.Serializable;
import java.util.ArrayList;

public abstract class GradeSection implements Serializable{

    private String _sectionName;
    private double _weight;
    private ArrayList<GradeSectionMark> _marks;

    public GradeSection(String sectionName, double weight){
        this._sectionName = sectionName;
        this._weight = weight;
        this._marks = new ArrayList<>();
    }

    public void addMark(GradeSectionMark mark){
        this._marks.add(mark);
    }

    public String getSectionName(){
        return this._sectionName;
    }

    public double getWeight(){
        return this._weight;
    }

    public String toString(){
        String retString = getSectionName() + "###" + getWeight();

        for (GradeSectionMark grade: _marks){
            retString += "###" + grade;
        }
        retString += "####";

        return retString;
    }

}
