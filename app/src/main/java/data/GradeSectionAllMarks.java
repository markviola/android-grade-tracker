package data;

/**
 * Object containing all the marks of a grade section (i.e assignments, tests, quizzes).
 * This GradeSection will take into account all the marks that are inputted when calculating the
 * user's grade section mark
 */
public class GradeSectionAllMarks extends GradeSection{

    private int _numMarks;
    private double _markWeight;

    public GradeSectionAllMarks(String sectionName, double weight){
        super(sectionName, weight);
//        this._numMarks = numMarks;
//        this._markWeight = markWeight;
    }

    @Override
    public double getSectionGrade() {
        double marksTotal = 0;
        int numApplicableMarks = 0;
        for(Mark mark: _marks){
            if(mark.getMark() != null){
                marksTotal += mark.getMark();
                numApplicableMarks++;
            }
        }
        return marksTotal/numApplicableMarks;
    }
}
