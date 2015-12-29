package data;


import java.io.Serializable;
import java.util.ArrayList;

public class Semester implements Serializable{

    private String _name;
    private ArrayList<Course> _courses;

    public Semester(String name, ArrayList<Course> courses){
        this._name = name;
        this._courses = courses;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public ArrayList<Course> getCourses() {
        return _courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this._courses = courses;
    }

    public String getCoursesStr(){
        String courses = "";

        for (Course course: _courses){
            courses += course.toString();
        }

        return courses;
    }

    public int numCourses(){
        return _courses.size();
    }

    public double getSGPA(){
        return 0;
    }
}
