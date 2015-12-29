package data;


import java.io.Serializable;

public class GradeSectionMark implements Serializable{

    private String _name;
    private double _mark;

    public GradeSectionMark(String name, double mark){
        this._name = name;
        this._mark = mark;
    }

    public String getName(){
        return this._name;
    }

    public double getMark(){
        return this._mark;
    }

    public void setMark(double newMark){
        this._mark = newMark;
    }

    public String toString(){
        return this._name + "##" + this._mark;
    }

}
