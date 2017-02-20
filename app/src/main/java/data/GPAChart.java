package data;


import java.util.HashMap;

public class GPAChart {

    private HashMap<String,Double> _gpaConversions;

    public GPAChart(){
        this._gpaConversions = new HashMap<>();
        addGPAElement("85-100", 4.0);
        addGPAElement("80-84", 3.7);
        addGPAElement("77-79", 3.3);
        addGPAElement("73-76", 3.0);
        addGPAElement("70-72", 2.7);
        addGPAElement("67-69", 2.3);
        addGPAElement("63-66", 2.0);
        addGPAElement("60-62", 1.7);
        addGPAElement("57-59", 1.3);
        addGPAElement("53-56", 1.0);
        addGPAElement("50-52", 0.7);
        addGPAElement("0-49", 0);
    }

    public void addGPAElement(String range, double value){
        this._gpaConversions.put(range, value);
    }

    public Double getGPAValue(Double mark){
        // Return 4.0 if the user has a mark higher than the maximum
        if(mark > 100){
            return 4.0;
        }

        for(String rangeStr: _gpaConversions.keySet()){
            String[] rangeStrArray = rangeStr.split("-");
            if((mark>=Double.parseDouble(rangeStrArray[0])-0.5)&&(mark<=Double.parseDouble(rangeStrArray[1])+0.5)){
                return _gpaConversions.get(rangeStr);
            }
        }
        return -1.0;
    }
}
