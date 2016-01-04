package data;


import java.io.Serializable;

public class Mark implements Serializable{

    private String _name;
    private Double _mark;
    //private double _weight;

    public Mark(String name, double mark){
        this._name = name;
        this._mark = mark;
    }

    public String getName(){
        return this._name;
    }

    public Double getMark(){
        return this._mark;
    }

//    public double getWeight(){
//        return this._weight;
//    }

    public void setMark(Double newMark){
        this._mark = newMark;
    }

    public String toString(){
        return this._name + "##" + this._mark;
    }

}
