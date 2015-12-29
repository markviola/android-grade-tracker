package data;


public class GradeSectionStaggered extends GradeSection {

    private int _numMarks;
    private double[] _markWeights;

    public GradeSectionStaggered(String sectionName, double weight, int numMarks,
                                 double[] markWeights){
        super(sectionName, weight);
        this._numMarks = numMarks;
        this._markWeights = markWeights;
    }
}
