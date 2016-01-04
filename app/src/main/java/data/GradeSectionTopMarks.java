package data;

/**
 * Object containing all the marks of a grade section (i.e assignments, tests, quizzes).
 * This GradeSection will take into account only a specific amount of the user's top marks when
 * calculating the grade section mark
 */
public class GradeSectionTopMarks extends GradeSection {

    private int _numMarks;

    public GradeSectionTopMarks(String sectionName, double weight, int numMarks){
        super(sectionName, weight);
        this._numMarks = numMarks;
    }

    @Override
    public double getSectionGrade() {
        return 0;
    }
}
