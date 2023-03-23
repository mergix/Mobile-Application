package com.example.mobilecoursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.mobilecoursework.Model.CustomAdapter
import com.example.mobilecoursework.Model.ListHub
import com.example.mobilecoursework.Model.Question
import com.example.mobilecoursework.Model.Survey
import com.example.surveyapplication.model.DataBaseHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StudentHome : AppCompatActivity() {


    lateinit var simpleList: ListView
    lateinit var Store : ListHub
    lateinit var surveyList : ArrayList<Survey>
    lateinit var questionList : ArrayList<Question>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        Store = ListHub(this)
        surveyList = Store.getSurveyList()


        //Create adapter
        simpleList = findViewById<ListView>(R.id.surveyListView)
        val customAdapter = CustomAdapter(applicationContext,surveyList)
        simpleList.isClickable = true
        simpleList!!.adapter = customAdapter
        listSelect()
    }
    fun listSelect(){
        var  rbGroup = findViewById<RadioGroup>(R.id.radioGroupAnswer)
        var rbQuestions = findViewById<RadioButton>(R.id.radioButtonAnswerQuestion)
        var titleText = findViewById<TextView>(R.id.textViewMessage)
        val mydatabase = DataBaseHelper(this)

        simpleList.setOnItemClickListener { Parent, view, position, id ->

            if (rbQuestions.isChecked){
                val Id = surveyList[position].Id
                val sTitle = surveyList[position].Title
                val startDate = surveyList[position].StartDate
                val endDate = surveyList[position].EndDate
                questionList = mydatabase.getallQuestionsBySurveyId(Id)


                var currentDate: LocalDate = LocalDate.now()
                var start: LocalDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                var end: LocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

                if(start > currentDate){
                    titleText.text = "The date to answer this survey hasn't reached yet please check back on the appropriate date "
                }else if (end < currentDate){
                    titleText.text = "This Survey has expired"
                }else if (!questionList.isEmpty()){
                    val i = Intent(this,StudentQuestionPage::class.java)
                    i.putExtra("id",Id)
                    i.putExtra("Title",sTitle)
                    i.putExtra("StartDate",startDate)
                    i.putExtra("EndDate",endDate)
                    startActivity(i)
                }else {
                    titleText.text =
                        "They are no Questions for the Survey you are trying to answer please try again later or pick another survey"
                }
            }

        }
    }
}