package com.example.mobilecoursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mobilecoursework.Model.Question
import com.example.surveyapplication.model.DataBaseHelper

class AdminCreateQuestion : AppCompatActivity() {


//    lateinit var questionList : ArrayList<Question>
//
//
//    var questiontextList :ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_create_question)
        val Id = intent.getIntExtra("id",0)

        val mydatabase = DataBaseHelper(this)
        val result0 = mydatabase.GetSurveyTitleById(Id)
        var title = findViewById<TextView>(R.id.textView3)
        title.text = result0

        var backbtn = findViewById<Button>(R.id.button2)
        backbtn.setOnClickListener {
            val i = Intent(this,AdminHome::class.java)
            startActivity(i)
        }
    }


    fun finishBtn(view: View){
        val Id = intent.getIntExtra("id",0)

        var Q1 = findViewById<EditText>(R.id.Question1).text.toString()
        var Q2 = findViewById<EditText>(R.id.Question2).text.toString()
        var Q3 = findViewById<EditText>(R.id.Question3).text.toString()
        var Q4 = findViewById<EditText>(R.id.Question4).text.toString()
        var Q5 = findViewById<EditText>(R.id.Question5).text.toString()
        var Q6 = findViewById<EditText>(R.id.Question6).text.toString()
        var Q7 = findViewById<EditText>(R.id.Question7).text.toString()
        var Q8 = findViewById<EditText>(R.id.Question8).text.toString()
        var Q9 = findViewById<EditText>(R.id.Question9).text.toString()
        var Q10 = findViewById<EditText>(R.id.Question10).text.toString()

        val newQuestions = Question(-1,Q1,Id!!.toInt())
        val newQuestions2 = Question(-1,Q2,Id!!.toInt())
        val newQuestions3 = Question(-1,Q3,Id!!.toInt())
        val newQuestions4 = Question(-1,Q4,Id!!.toInt())
        val newQuestions5 = Question(-1,Q5,Id!!.toInt())
        val newQuestions6 = Question(-1,Q6,Id!!.toInt())
        val newQuestions7 = Question(-1,Q7,Id!!.toInt())
        val newQuestions8 = Question(-1,Q8,Id!!.toInt())
        val newQuestions9 = Question(-1,Q9,Id!!.toInt())
        val newQuestions10 = Question(-1,Q10,Id!!.toInt())
        val mydatabase = DataBaseHelper(this)
        val result = mydatabase.addQuestionToSurveyId(newQuestions)

        when(result) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }

        val result2 = mydatabase.addQuestionToSurveyId(newQuestions2)
        when(result2) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result3 = mydatabase.addQuestionToSurveyId(newQuestions3)
        when(result3) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result4 = mydatabase.addQuestionToSurveyId(newQuestions4)
        when(result4) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result5 = mydatabase.addQuestionToSurveyId(newQuestions5)
        when(result5) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result6 = mydatabase.addQuestionToSurveyId(newQuestions6)
        when(result6) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result7 = mydatabase.addQuestionToSurveyId(newQuestions7)
        when(result7) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result8 = mydatabase.addQuestionToSurveyId(newQuestions8)
        when(result8) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result9 = mydatabase.addQuestionToSurveyId(newQuestions9)
        when(result9) {
            1 ->  {
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
        val result10 = mydatabase.addQuestionToSurveyId(newQuestions10)
        when(result10) {
            1 ->  {
                Toast.makeText(this,"Your Questions have been added to the Survey ", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AdminHome::class.java)
                startActivity(intent)
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
    }
}