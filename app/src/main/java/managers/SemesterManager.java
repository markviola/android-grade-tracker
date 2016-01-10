package managers;


import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

import data.DBManager;
import data.Semester;

public class SemesterManager implements Serializable{

    private static SemesterManager instance;
    private ArrayList<Semester> _semesters;
    DBManager dbManager;

    public SemesterManager(Context context){
        this._semesters = new ArrayList<>();
        dbManager = DBManager.getInstance(context);
    }

    public boolean addSemester(Semester newSemester){
        if(!inSemesterManager(newSemester.getName())){
            this._semesters.add(newSemester);
            dbManager.addSemester(newSemester);
            return true;
        }
        return false;
    }

    public ArrayList<Semester> getSemesters(){
        return this._semesters;
    }

    public static SemesterManager getInstance(Context context) {
        if (instance == null) {
            instance = new SemesterManager(context);
        }
        return instance;
    }

    /**
     * Return the semester corresponding to the input semester name
     * @param semesterName Te name of the semester being searched
     * @return The semester corresponding to the input semester name
     */
    public Semester getSemester(String semesterName){
        for(Semester semester: _semesters){
            if(semester.getName().equals(semesterName)){
                return semester;
            }
        }
        return null;
    }


    public void setSemesters(ArrayList<Semester> newSemesters){
        this._semesters = newSemesters;
    }

    /**
     * Return True iff the input name is already a semester name in the semester manager
     * @param semesterName The name of the semester that is being searched
     * @return True iff the input name is already a semester name in the semester manager
     */
    public boolean inSemesterManager(String semesterName){
        for(int i=0; i<_semesters.size(); i++){
            if(_semesters.get(i).getName().equals(semesterName)){
                return true;
            }
        }

        return false;
    }
}
