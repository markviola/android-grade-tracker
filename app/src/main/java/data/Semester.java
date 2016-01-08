package data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Semester implements Serializable{

    private String _name;
    private ArrayList<Course> _courses;
    private GPAChart _gpaChart;

    public Semester(String name, ArrayList<Course> courses){
        this._name = name;
        this._courses = courses;
        this._gpaChart = new GPAChart();
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
        Double totalGPAValue = 0.0;
        for(Course course: _courses){
            totalGPAValue += _gpaChart.getGPAValue(course.getCurrentGrade());
        }
        return totalGPAValue/_courses.size();
    }
}
