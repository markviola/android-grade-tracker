package managers;


import java.io.Serializable;
import java.util.ArrayList;

import data.Semester;

public class SemesterManager implements Serializable{

    private static SemesterManager instance;
    private ArrayList<Semester> _semesters;


    public SemesterManager(){
        this._semesters = new ArrayList<>();
    }

    public void addSemester(Semester semester){
        this._semesters.add(semester);
    }

    public ArrayList<Semester> getSemesters(){
        return this._semesters;
    }

    public static SemesterManager getInstance() {
        if (instance == null) {
            instance = new SemesterManager();
        }
        return instance;
    }

    public Semester getSemester(String semesterName){
        for(Semester semester: _semesters){
            if(semester.getName().equals(semesterName)){
                return semester;
            }
        }
        return null;
    }
}
