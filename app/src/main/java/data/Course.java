package data;


import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{

    private String _name;
    private String _code;
    private ArrayList<GradeSection> _grades;

    public Course(String name){
        this._name = name;
        this._code = "N/A";
        this._grades = new ArrayList<>();
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

    public void setCode(String newCode){
        this._code = newCode;
    }

    public ArrayList<GradeSection> getGrade() {
        return _grades;
    }

    public void addGradeSection(GradeSection newGrade){
        this._grades.add(newGrade);
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
