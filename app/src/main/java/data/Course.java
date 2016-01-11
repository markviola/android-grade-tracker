package data;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Course implements Serializable{

    private String TAG = "customFilter";

    private String _name;
    private String _code;
    private ArrayList<GradeSection> _grades;
    private boolean _inProgress;

    public Course(String name, String code){
        this._name = name;

        if(code != null){
            this._code = code;
        } else {
            this._code = "N/A";
        }

        this._grades = new ArrayList<>();
        this._inProgress = true;
    }

    public void setGrade(ArrayList<GradeSection> grade) {
        this._grades = grade;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getCode(){
        return this._code;
    }

    public boolean getInProgress(){
        return this._inProgress;
    }

    public void setInProgress(boolean inProgress){
        this._inProgress = inProgress;
    }

    public void setCode(String newCode){
        this._code = newCode;
    }

    public ArrayList<GradeSection> getGrade() {
        return _grades;
    }

    public void addGradeSection(GradeSection newGrade){
        this._grades.add(newGrade);
    }

    public GradeSection getGradeSectionByName(String gradeSectionName){
        for(GradeSection gradeSection: _grades){
            if(gradeSection.getSectionName().equals(gradeSectionName)){
                return gradeSection;
            }
        }
        return null;
    }

    public void deleteGradeSectionByName(String gradeSectionName){
        Iterator<GradeSection> gradeSectionIterator = _grades.iterator();

        while(gradeSectionIterator.hasNext()){
            GradeSection gradeSection = gradeSectionIterator.next();
            if(gradeSection.getSectionName().equals(gradeSectionName)){
                _grades.remove(gradeSection);
                break;
            }
        }
    }

    public void deleteMarkByName(String gradeSectionName, String markName){
        Iterator<GradeSection> gradeSectionIterator = _grades.iterator();

        while(gradeSectionIterator.hasNext()){
            GradeSection gradeSection = gradeSectionIterator.next();
            if(gradeSection.getSectionName().equals(gradeSectionName)){
                Iterator<Mark> markIterator = gradeSection.getMarks().iterator();
                while (markIterator.hasNext()){
                    Mark mark = markIterator.next();
                    if(mark.getName().equals(markName)){
                        gradeSection.getMarks().remove(mark);
                        break;
                    }
                }
                break;
            }
        }
    }

    public double getCurrentGrade(){
        if(_grades.size() == 0){ //No marks inputted
            return -1;
        } else {
            double totalWeightedGrades = 0.0;
            double totalWeights = 0.0;
            for(GradeSection gradeSection: _grades){
                if(gradeSection.numMarks() > 0){
                    totalWeightedGrades += gradeSection.getSectionGrade() * gradeSection.getWeight();
                    totalWeights += gradeSection.getWeight();
                }
            }

            return totalWeightedGrades/totalWeights;
        }
    }

    public String toString(){
        String retString = getName()+"@@@@" +getCode();
        for(GradeSection grade: _grades){
            retString += "@@@@" + grade.toString();
        }
        retString += "@@@@@";

        return retString;
    }

    public String gradesStr(){
        return null;
    }
}
