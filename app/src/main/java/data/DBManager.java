package data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;

import data.Semester;

public class DBManager extends SQLiteOpenHelper{

    private static final String TAG = "customFilter";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SemesterDatabase.db";

    //Information for Player table
    public static final String TABLE_SEMESTERS = "_semesters";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SEMESTER = "_semesterName";
    public static final String COLUMN_COURSES = "_courses";


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "In onCreate DBManager");

        //Create Grades table
        String playerQuery = "CREATE TABLE " + TABLE_SEMESTERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SEMESTER + " TEXT, " +
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
        values.put(COLUMN_SEMESTER, semester.getName());
        values.put(COLUMN_COURSES, semester.getCoursesStr());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_SEMESTERS, null, values);
        db.close();
    }

    //Get the courses corresponding to a particular semester
    public String dbGetCourses(String semesterName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SEMESTERS + " WHERE 1";

        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            Log.i(TAG,cur.getString(cur.getColumnIndex(COLUMN_SEMESTER)));
            if (cur.getString(cur.getColumnIndex(COLUMN_SEMESTER)).equals(semesterName)) {
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
            semesterNames.add(cur.getString(cur.getColumnIndex(COLUMN_SEMESTER)));
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

        //Log.i(TAG, "!!!!!!!!!!!!! FIRST FOR LOOP !!!!!!!!!!!!!");
        for(String courseStr: strCourses){
            //Log.i(TAG, "Courses: " + courseStr);
            String[] gradeSecStr = courseStr.split("@@@@");
            //Log.i(TAG, gradeSecStr[0]);
            Course newCourse = new Course(gradeSecStr[0]);

            //Log.i(TAG, "!!!!!!!!!!!!! SECOND FOR LOOP !!!!!!!!!!!!!");
            for(int i=1; i<gradeSecStr.length; i++){
                //Log.i(TAG, "GradeSection: " + gradeSecStr[i]);
                String[] gradeSecMarksStr = gradeSecStr[i].split("####");

                for(int k=0; k <gradeSecMarksStr.length; k++){
                    String[] gradeSecMarkStr = gradeSecMarksStr[k].split("###");
                    //Log.i(TAG, gradeSecMarkStr[0] + ", " + gradeSecMarkStr[1]);
                    GradeSection newGradeSec = new GradeSectionEven(gradeSecMarkStr[0],
                            Double.parseDouble(gradeSecMarkStr[1]));

                    //Log.i(TAG, "!!!!!!!!!!!!! THIRD FOR LOOP !!!!!!!!!!!!!");
                    for (int j=2; j<gradeSecMarkStr.length; j++){
                        //Log.i(TAG, "GradeSectionMark: " + gradeSecMarkStr[j]);
                        String[] gradeSecMarkDataStr = gradeSecMarkStr[j].split("##");
                        //Log.i(TAG, gradeSecMarkDataStr[0] + ", " + gradeSecMarkDataStr[1]);
                        GradeSectionMark newGradeSecMark = new GradeSectionMark(gradeSecMarkDataStr[0],
                                Double.parseDouble(gradeSecMarkDataStr[1]));
                        newGradeSec.addMark(newGradeSecMark);
                    }

                    newCourse.addGradeSection(newGradeSec);
                }

            }
            reCourses.add(newCourse);
        }

        return new Semester(semesterName, reCourses);
    }

}
