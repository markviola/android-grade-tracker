package data;


import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{

    private String _name;
    private String _code;
    private ArrayList<GradeSection> _grades;
    private boolean _inProgress;

    public Course(String name){
        this._name = name;
        this._code = "N/A";
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

    public double getCurrentGrade(){
        double totalWeightedGrades = 0.0;
        double totalWeights = 0.0;
        for(GradeSection gradeSection: _grades){
            totalWeightedGrades += gradeSection.getSectionGrade() * gradeSection.getWeight();
            totalWeights += gradeSection.getWeight();
        }

        return totalWeightedGrades/totalWeights;
    }

    public String toString(){
        String retString = getName();
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
