package com.example.mobilecoursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.*
import com.example.mobilecoursework.Model.CustomAdapter
import com.example.mobilecoursework.Model.ListHub
import com.example.mobilecoursework.Model.Question
import com.example.mobilecoursework.Model.Survey
import com.example.surveyapplication.model.DataBaseHelper

class AdminHome : AppCompatActivity() {

    lateinit var simpleList: ListView

    lateinit var myStore :  ListHub
    lateinit var surveyList : ArrayList<Survey>
    lateinit var questionList : ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        myStore = ListHub(this)
        surveyList = myStore.getSurveyList()

        simpleList = findViewById<ListView>(R.id.surveyListView)
        //Create an adapter
        val customAdapter = CustomAdapter(applicationContext,surveyList)
        simpleList.isClickable = true
        simpleList!!.adapter = customAdapter
        listSelect()
    }


    fun Createbtn(view: View){
       var surveyId =  findViewById<TextView>(R.id.textViewId).text.toString()
        val intent = Intent(this, AdminCreateSurvey::class.java).apply {
            putExtra(EXTRA_MESSAGE, surveyId)
        }
        startActivity(intent)
    }

    fun listSelect(){
        var  rbGroup = findViewById<RadioGroup>(R.id.radioGroupAnswer)
        var rbQuestions = findViewById<RadioButton>(R.id.radioButtonAnswerQuestion)
        var rbQuestionsEdit = findViewById<RadioButton>(R.id.radioButtonEditQuestions)
        var rbEdit= findViewById<RadioButton>(R.id.radioButtonEditSurvey)
        var rbDelete = findViewById<RadioButton>(R.id.radioButtonDeleteSurvey)
        var rbStats = findViewById<RadioButton>(R.id.radioButtonStats)

            simpleList.setOnItemClickListener { Parent, view, position, id ->

                if (rbQuestions.isChecked){
                    val Id = surveyList[position].Id
                    val sTitle = surveyList[position].Title
                    val startDate = surveyList[position].StartDate
                    val endDate = surveyList[position].EndDate

                    val mydatabase = DataBaseHelper(this)
                    questionList = mydatabase.getallQuestionsBySurveyId(Id)

                    if (questionList.size < 9) {
                        val i = Intent(this, AdminCreateQuestion::class.java)
                        i.putExtra("id", Id)
                        i.putExtra("Title", sTitle)
                        i.putExtra("StartDate", startDate)
                        i.putExtra("EndDate", endDate)
                        startActivity(i)
                    }else {
                        Toast.makeText(
                            this,
                            "They are already questions for this Survey ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else if (rbEdit.isChecked){
                    val Id = surveyList[position].Id
                    val sTitle = surveyList[position].Title
                    val startDate = surveyList[position].StartDate
                    val endDate = surveyList[position].EndDate

                    val mydatabase = DataBaseHelper(this)
                    questionList = mydatabase.getallQuestionsBySurveyId(Id)

                        val i = Intent(this, AdminEditSurvey::class.java)
                        i.putExtra("id", Id)
                        i.putExtra("Title", sTitle)
                        i.putExtra("StartDate", startDate)
                        i.putExtra("EndDate", endDate)
                        startActivity(i)
                } else if (rbDelete.isChecked){
                    val Id = surveyList[position].Id
                    val sTitle = surveyList[position].Title
                    val startDate = surveyList[position].StartDate
                    val endDate = surveyList[position].EndDate
                        val i = Intent(this, AdminDeleteSurvey::class.java)
                        i.putExtra("id", Id)
                        i.putExtra("Title", sTitle)
                        i.putExtra("StartDate", startDate)
                        i.putExtra("EndDate", endDate)
                        startActivity(i)

                }else if(rbQuestionsEdit.isChecked){
                    val Id = surveyList[position].Id
                    val sTitle = surveyList[position].Title
                    val startDate = surveyList[position].StartDate
                    val endDate = surveyList[position].EndDate

                    val mydatabase = DataBaseHelper(this)
                    questionList = mydatabase.getallQuestionsBySurveyId(Id)
                    if (!questionList.isEmpty()) {
                        val i = Intent(this, AdminEditQuestions::class.java)
                        i.putExtra("id", Id)
                        i.putExtra("Title", sTitle)
                        i.putExtra("StartDate", startDate)
                        i.putExtra("EndDate", endDate)
                        startActivity(i)
                    }else {
                        Toast.makeText(
                            this,
                            "They are no Questions for the Survey you are trying to edit ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else if(rbStats.isChecked){
                    val Id = surveyList[position].Id
                    val sTitle = surveyList[position].Title
                    val startDate = surveyList[position].StartDate
                    val endDate = surveyList[position].EndDate

                    val mydatabase = DataBaseHelper(this)
                    questionList = mydatabase.getallQuestionsBySurveyId(Id)
                    if (!questionList.isEmpty()) {
                        val i = Intent(this, AdminResults::class.java)
                        i.putExtra("id", Id)
                        i.putExtra("Title", sTitle)
                        i.putExtra("StartDate", startDate)
                        i.putExtra("EndDate", endDate)
                        startActivity(i)
                    }else {
                        Toast.makeText(
                            this,
                            "They are no Stats for the Survey",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

