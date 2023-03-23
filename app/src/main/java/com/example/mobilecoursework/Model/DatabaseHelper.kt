package com.example.surveyapplication.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobilecoursework.Model.*

/* Database Config*/
private val DataBaseName = "SurveyAppDataBase.db"
private val ver : Int = 1

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context,DataBaseName,null ,ver) {

    /* Student Table */
    private val StudentTableName = "Student"
    private val StudentColumnID = "Id"
    private val StudentColumnLoginName = "LoginName"
    private val StudentColumnPassword = "Password"
    /*************************/
    /* Question Table */
    private val QuestionTableName = "Question"
    private val QuestionColumnID = "Id"
    private val QuestionColumnSurveyId = "SurveyId"
    private val QuestionColumnQuestionText =   "QuestionText"

    /*************************/
    /* Survey Table */
    private val SurveyTableName = "Survey"
    private val SurveyColumnID = "Id"
    private val SurveyColumnTitle = "Title"
    private val SurveyColumnStartDate = "StartDate"
    private val SurveyColumnEndDate = "EndDate"
    private val SurveyColumnStudentId = "StudentId"
    /*************************/
    /* Admin Table */
    private val AdminTableName = "Admin"
    private val AdminColumnID = "Id"
    private val AdminColumnLoginName = "LoginName"
    private val AdminColumnPassword = "Password"
    /*************************/
    /* Answer Table */
    private val AnswerTableName = "Answer"
    private val AnswerId = "Id"
    private val AnswerTextColumn = "AnswerText"
    private val AnswerStudentId = "StudentId"
    private val AnswerQuestionId = "QuestionId"

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            var sqlCreateStatement: String = "CREATE TABLE " +
                    StudentTableName + " ( " +
                    StudentColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StudentColumnLoginName + " TEXT NOT NULL UNIQUE, " +
                    StudentColumnPassword + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    QuestionTableName + "(" +
                    QuestionColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QuestionColumnQuestionText + " TEXT NOT NULL, " +
                    QuestionColumnSurveyId + " INTEGER  REFERENCES "+
                    SurveyTableName + "(" + SurveyColumnID + "))"
            db?.execSQL(sqlCreateStatement)

             sqlCreateStatement = "CREATE TABLE " +
                    SurveyTableName + "(" +
                    SurveyColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SurveyColumnTitle +  " TEXT NOT NULL, "+
                     SurveyColumnStartDate +  " TEXT NOT NULL, "+
                    SurveyColumnEndDate +  " TEXT NOT NULL, "+
                    SurveyColumnStudentId +" INTEGER  REFERENCES " +
                    StudentTableName + "("+ StudentColumnID + "))"

            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    AdminTableName + "( " +
                    AdminColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AdminColumnLoginName + " TEXT NOT NULL UNIQUE, " +
                    AdminColumnPassword + " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)
            sqlCreateStatement = "CREATE TABLE " +
                    AnswerTableName + "( " + AnswerId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AnswerTextColumn + " TEXT NOT NULL,"+
                    AnswerStudentId +" INTEGER  REFERENCES " +
                    StudentTableName + "("+ StudentColumnID + "),"+
                    AnswerQuestionId +" INTEGER  REFERENCES " +
                    QuestionTableName + "("+ QuestionColumnID + "))"

            db?.execSQL(sqlCreateStatement)

        }
        catch (e: SQLiteException) {}
    }

    // This is called if the database ver. is changed
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
        /* db?.execSQL("DROP TABLE IF EXISTS")*/
    }

    /**
     * return  1 : the new use has been add to the database successfully
     * return -1 : Error, adding new user
     * return -2 : can not open database
     * return -3 : user name is already exist
     *
     */
    /* Student Methods*/
    fun addStudent(student: Student) : Int {

        val isUserNameAlreadyExists = checkUserName(student) // check if the username is already exist in the database
        if(isUserNameAlreadyExists < 0)
            return isUserNameAlreadyExists

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()


        cv.put(StudentColumnLoginName, student.LoginName.lowercase())
        cv.put(StudentColumnPassword, student.Password)

        val success  =  db.insert(StudentTableName, null, cv)
        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return 1

    }
    fun getStudent(student: Student) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = student.LoginName.lowercase()
        val userPassword = student.Password
        //val sqlStatement = "SELECT * FROM $TableName WHERE $Column_UserName = $userName AND $Column_Password = $userPassword"

        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $StudentColumnLoginName = ? AND $StudentColumnPassword = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //User not found

    }
    private fun checkUserName(student: Student): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = student.LoginName.lowercase()

        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $StudentColumnLoginName = ?"
        val param = arrayOf(userName)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // error the user name is already exist
        }

        cursor.close()
        db.close()
        return 0 //User not found

    }

    /* Question Methods*/
    fun addQuestionToSurveyId(question: Question) :Int{
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(QuestionColumnQuestionText,question.QuestionText)
        cv.put(QuestionColumnSurveyId,question.SurveyId)

        val success  =  db.insert(QuestionTableName, null, cv)
        db.close()
        if (success.toInt() == -1) return success.toInt()
        else return 1
    }


    fun getallQuestionsBySurveyId(Id :Int): ArrayList<Question>{
        val questionList = ArrayList<Question>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT  * FROM $QuestionTableName INNER JOIN $SurveyTableName WHERE $QuestionColumnSurveyId = $Id "

        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val QuestionText: String = cursor.getString(1)
                val SurveyId: Int = cursor.getInt(2)

                val kk = Question(id,QuestionText,SurveyId)
                questionList.add(kk)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return questionList
    }

    fun updateQuestions(question: Question) : Int {

        val db: SQLiteDatabase = this.writableDatabase

        val cv: ContentValues = ContentValues()
        cv.put(QuestionColumnID,question.Id)
        cv.put(QuestionColumnQuestionText,question.QuestionText)
        cv.put(QuestionColumnSurveyId,question.SurveyId)

        val success = db.update(QuestionTableName,cv,"Id="+ question.Id,null)
        db.close()
        return success
    }

    fun deleteQuestionById(Id :Int) :Int {
        val db: SQLiteDatabase = this.writableDatabase

        val cv: ContentValues = ContentValues()
        cv.put(SurveyColumnID,Id)

        val success = db.delete(SurveyTableName,"id=" + Id,null)
        db.close()
        return success
    }



    /* Survey Methods*/
    fun addSurvey(survey: Survey) :Int{

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()
        cv.put(SurveyColumnTitle,survey.Title)
        cv.put(SurveyColumnStartDate,survey.StartDate)
        cv.put(SurveyColumnEndDate,survey.EndDate)
        val success  =  db.insert(SurveyTableName, null, cv)

        db.close()
        if (success.toInt() == -1)
            return success.toInt() //Error, adding new user
        else return 1
    }
    fun updateSurvey(survey: Survey) : Int {

        val db: SQLiteDatabase = this.writableDatabase

        val cv: ContentValues = ContentValues()
        cv.put(SurveyColumnID,survey.Id)
        cv.put(SurveyColumnTitle,survey.Title)
        cv.put(SurveyColumnStartDate,survey.StartDate)
        cv.put(SurveyColumnEndDate,survey.EndDate)


        val success = db.update(SurveyTableName,cv,"Id="+ survey.Id,null)
        db.close()
        return success
    }
    fun deleteSurveyById(Id :Int) :Int {
        val db: SQLiteDatabase = this.writableDatabase

        val cv: ContentValues = ContentValues()
        cv.put(SurveyColumnID,Id)

        val success = db.delete(SurveyTableName,"id=" + Id,null)
        db.close()
        return success
    }

    fun GetSurveyTitleById(Id :Int) :String {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT $SurveyColumnTitle FROM $SurveyTableName WHERE  $SurveyColumnID = $Id"


        var title : String = ""
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                title = cursor.getString(0)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return title
    }

    fun getallSurveys(): ArrayList<Survey>{
        val SurveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTableName"

        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val title: String = cursor.getString(1)
                val startdate: String = cursor.getString(2)
                val enddate: String = cursor.getString(3)
                val studentId: Int = cursor.getInt(4)

                val kk = Survey(id,title,startdate,enddate,studentId)
                SurveyList.add(kk)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return SurveyList
    }

    /* Admin Methods*/
    fun addAdmin(admin: Admin) : Int {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()


        cv.put(AdminColumnLoginName, admin.LoginName.lowercase())
        cv.put(AdminColumnPassword, admin.Password)

        val success  =  db.insert(AdminTableName, null, cv)
        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return 1

    }

    fun getAdmin(admin: Admin) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = admin.LoginName.lowercase()
        val userPassword = admin.Password
        //val sqlStatement = "SELECT * FROM $TableName WHERE $Column_UserName = $userName AND $Column_Password = $userPassword"

        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $AdminColumnLoginName = ? AND $AdminColumnPassword = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //User not found

    }

    /* Answer Methods*/

    fun addAnswersToQuestionId(answer: Answer):Int {

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(AnswerTextColumn,answer.answerText)
        cv.put(AnswerQuestionId,answer.QuestionId)

        val success  =  db.insert(AnswerTableName, null, cv)
        db.close()
        if (success.toInt() == -1) return success.toInt()
        else return 1
    }

    fun getallAnswersByQuestionId(Id :Int): ArrayList<Answer>{
        val answerList = ArrayList<Answer>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $AnswerTableName INNER JOIN $QuestionTableName WHERE $AnswerQuestionId = $Id "

        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val AnswerText: String = cursor.getString(1)
                val StudentId: Int = cursor.getInt(2)
                val QuestionId: Int = cursor.getInt(3)

                val kk = Answer(id,AnswerText,StudentId,QuestionId)
                answerList.add(kk)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return answerList
    }

    fun AnswerStatsOfSurveyQuestionStronglyAgree(Id :Int): Int{
        val answerList = ArrayList<Answer>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT COUNT($AnswerTextColumn) FROM $AnswerTableName WHERE $AnswerTextColumn ='Strongly Agree' AND $AnswerQuestionId = $Id"


        var count : Int = 0
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
              count = cursor.getInt(0)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return count
    }

    fun AnswerStatsOfSurveyQuestionAgree(Id :Int): Int{
        val answerList = ArrayList<Answer>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT COUNT($AnswerTextColumn) FROM $AnswerTableName WHERE $AnswerTextColumn ='Agree' AND $AnswerQuestionId = $Id"



        var count : Int = 0
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                count = cursor.getInt(0)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return count
    }

    fun AnswerStatsOfSurveyQuestionNeither(Id :Int): Int{
        val answerList = ArrayList<Answer>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT COUNT($AnswerTextColumn) FROM $AnswerTableName WHERE $AnswerTextColumn ='Neither Agree Nor Disagree' AND $AnswerQuestionId = $Id"



        var count : Int = 0
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                count = cursor.getInt(0)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return count
    }

    fun AnswerStatsOfSurveyQuestionDisAgree(Id :Int): Int{
        val answerList = ArrayList<Answer>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT COUNT($AnswerTextColumn) FROM $AnswerTableName WHERE $AnswerTextColumn ='Disagree' AND $AnswerQuestionId = $Id"



        var count : Int = 0
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                count = cursor.getInt(0)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return count
    }

    fun AnswerStatsOfSurveyQuestionStronglyDisAgree(Id :Int): Int{
        val answerList = ArrayList<Answer>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT COUNT($AnswerTextColumn) FROM $AnswerTableName WHERE $AnswerTextColumn ='Strongly Disagree' AND $AnswerQuestionId = $Id"


        var count : Int = 0
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)

        if(cursor.moveToFirst())
            do {
                count = cursor.getInt(0)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return count
    }


}