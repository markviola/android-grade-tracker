package data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper{

    private static DBManager instance;
    private static final String TAG = "customFilter";

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "SemesterDatabase.db";

    //Information for Player table
    public static final String TABLE_SEMESTERS = "_semesters";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SEMESTER_NAMES = "_semesterName";
    public static final String COLUMN_COURSES = "_courses";


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public static DBManager getInstance(Context context){
        if (instance == null) {
            instance = new DBManager(context,null,null,1);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Grades table
        String playerQuery = "CREATE TABLE " + TABLE_SEMESTERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SEMESTER_NAMES + " TEXT, " +
                COLUMN_COURSES + " TEXT " +
                ");";
        db.execSQL(playerQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEMESTERS);
        onCreate(db);
    }

    //Check if database is empty
    public boolean isEmpty(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SEMESTERS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);

        return c.getCount() == 0;
    }

    //Add semester to the database
    public void addSemester(Semester semester){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SEMESTER_NAMES, semester.getName());
        values.put(COLUMN_COURSES, semester.getCoursesStr());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SEMESTERS, null, values);
        db.close();
    }

    public void deleteSemester(String semesterName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SEMESTERS + " WHERE " + COLUMN_SEMESTER_NAMES + "=\"" + semesterName + "\";");

    }

    public void updateSemesterInfo(String semesterName, String newInfo){
        String query = "UPDATE " + TABLE_SEMESTERS + " SET "+ COLUMN_COURSES + " = \'" + newInfo +
                "\' WHERE "+ COLUMN_SEMESTER_NAMES +" = \'" + semesterName + "\'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }

    //Get the courses corresponding to a particular semester
    public String dbGetCourses(String semesterName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SEMESTERS + " WHERE 1";

        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            if (cur.getString(cur.getColumnIndex(COLUMN_SEMESTER_NAMES)).equals(semesterName)) {
                String courses = cur.getString(cur.getColumnIndex(COLUMN_COURSES));
                db.close();
                cur.close();
                return courses;
            }
            cur.moveToNext();
        }
        db.close();
        cur.close();
        return "ERROR STATE";
    }

    public ArrayList<String> getAllSemesterNames(){
        ArrayList<String> semesterNames = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SEMESTERS + " WHERE 1";

        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            semesterNames.add(cur.getString(cur.getColumnIndex(COLUMN_SEMESTER_NAMES)));
            cur.moveToNext();
        }
        db.close();
        cur.close();
        return semesterNames;
    }

    public Semester getSemesterStrToObj(String semesterName){

        String dbStr = dbGetCourses(semesterName);
        ArrayList<Course> reCourses = new ArrayList<>();
        String[] strCourses = dbStr.split("@@@@@");

        if(!dbStr.equals("")){
            for(String courseStr: strCourses){
                String[] gradeSecStr = courseStr.split("@@@@");
                Course newCourse = new Course(gradeSecStr[0],gradeSecStr[1]);

                for(int i=2; i<gradeSecStr.length; i++){
                    String[] gradeSecMarksStr = gradeSecStr[i].split("####");

                    for(int k=0; k <gradeSecMarksStr.length; k++){
                        String[] gradeSecMarkStr = gradeSecMarksStr[k].split("###");
                        GradeSection newGradeSec = new GradeSectionAllMarks(gradeSecMarkStr[0],
                                Double.parseDouble(gradeSecMarkStr[1]));

                        for (int j=2; j<gradeSecMarkStr.length; j++){
                            String[] gradeSecMarkDataStr = gradeSecMarkStr[j].split("##");
                            Mark newGradeSecMark;
                            if(!gradeSecMarkDataStr[1].equals("null")){
                                newGradeSecMark = new Mark(gradeSecMarkDataStr[0],
                                        Double.parseDouble(gradeSecMarkDataStr[1])
                                        //,Double.parseDouble(gradeSecMarkDataStr[2]) //For if grades have individual weights
                                );
                            } else {
                                newGradeSecMark = new Mark(gradeSecMarkDataStr[0],
                                        null
                                        //,Double.parseDouble(gradeSecMarkDataStr[2]) //For if grades have individual weights
                                );
                            }
                            newGradeSec.addMark(newGradeSecMark);
                        }
                        newCourse.addGradeSection(newGradeSec);
                    }
                }
                reCourses.add(newCourse);
            }
        }

        return new Semester(semesterName, reCourses);
    }

    public ArrayList<Semester> getAllSemesters(){
        ArrayList<Semester> allSemesters = new ArrayList<>();
        ArrayList<String> allSemesterNames = getAllSemesterNames();

        for(String semesterName: allSemesterNames){
            allSemesters.add(getSemesterStrToObj(semesterName));
        }

        return allSemesters;
    }

}
