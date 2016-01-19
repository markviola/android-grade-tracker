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

    public void addCourse(Course newCourse){
        this._courses.add(newCourse);
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
        int numValidCourses = 0;
        for(Course course: _courses){
            if(course.getCurrentGrade() != -1){ //There is some valid mark inputted
                totalGPAValue += _gpaChart.getGPAValue(course.getCurrentGrade());
                numValidCourses++;
            }
        }

        //Check for the case when there is one course in a semester without any marks
        if(numValidCourses > 0){
            return totalGPAValue/numValidCourses;
        } else {
            return -1;
        }

    }

    public Course getCourseByName(String courseName){
        for(Course course: _courses){
            if(course.getName().equals(courseName)){
                return course;
            }
        }
        return null;
    }

    public void deleteCourseByName(String courseName){
        for(Course course: _courses){
            if(course.getName().equals(courseName)){
                _courses.remove(course);
                break;
            }
        }
    }
}
