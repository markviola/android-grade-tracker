package data;


import java.util.HashMap;

public class GPAConversion {

    private HashMap<String,Double> _gpaConversions;

    public GPAConversion(){
        this._gpaConversions = new HashMap<>();
    }

    public void addGPAElement(String range, double value){
        this._gpaConversions.put(range, value);
    }
}
