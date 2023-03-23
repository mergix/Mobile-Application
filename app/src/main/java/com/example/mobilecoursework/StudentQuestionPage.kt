package com.example.mobilecoursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mobilecoursework.Model.Answer
import com.example.mobilecoursework.Model.Question
import com.example.surveyapplication.model.DataBaseHelper

class StudentQuestionPage : AppCompatActivity() {


    lateinit var questionList : ArrayList<Question>
    lateinit var questionList10 : ArrayList<Question>

    var questionIdList :ArrayList<Int> = ArrayList()
    var questiontextList :ArrayList<String> = ArrayList()
    var questionCounter :Int = 0
    var questionCounterTotal :Int = 0
    lateinit var currentQuestion : Question
    lateinit var currentQuestionid : Question
    var Qid :Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_question_page)




        val Id = intent.getIntExtra("id",0)
        
        val mydatabase = DataBaseHelper(this)
        var surveyTitle = mydatabase.GetSurveyTitleById(Id)
        questionList = mydatabase.getallQuestionsBySurveyId(Id)
       questionList10 = questionList.take(10) as ArrayList<Question>
        questionCounterTotal = questionList10.size
        nextQuestion()
        var titleText = findViewById<TextView>(R.id.textViewSurveyTitle)
        titleText.text = surveyTitle
    }

    fun nextQuestion() {

        var rb1 = findViewById<RadioButton>(R.id.radioButtonSagree)
        var rb2 = findViewById<RadioButton>(R.id.radioButtonAgree)
        var rb3 = findViewById<RadioButton>(R.id.radioButtonNeither)
        var rb4 = findViewById<RadioButton>(R.id.radioButtonDisagree)
        var rb5 = findViewById<RadioButton>(R.id.radioButtonSdisagree)

        var questionText = findViewById<TextView>(R.id.textViewQuestionText)
        var counter = findViewById<TextView>(R.id.textViewQuestionCounter)

        if (questionCounter < questionCounterTotal){
            currentQuestion = questionList10.get(questionCounter)
            currentQuestionid = questionList10.get(questionCounter)

            Qid = currentQuestionid.Id
            questionText.text = currentQuestion.QuestionText

            questionCounter++
            counter.text = "Question: $questionCounter/$questionCounterTotal"
        }
        else{
            var btnNext = findViewById<Button>(R.id.buttonNextConfirm)
            var rbGroupA = findViewById<RadioGroup>(R.id.AnswerGroup)
            rb1.setClickable(false)
            rb2.setClickable(false)
            rb3.setClickable(false)
            rb4.setClickable(false)
            rb5.setClickable(false)

            btnNext.text = "Finish Survey"
            btnNext.setOnClickListener {
                val i = Intent(this,StudentHome::class.java)
                startActivity(i)
            }
        }
    }


    fun nextBtn(view: View){
        var rb1 = findViewById<RadioButton>(R.id.radioButtonSagree)
        var rb2 = findViewById<RadioButton>(R.id.radioButtonAgree)
        var rb3 = findViewById<RadioButton>(R.id.radioButtonNeither)
        var rb4 = findViewById<RadioButton>(R.id.radioButtonDisagree)
        var rb5 = findViewById<RadioButton>(R.id.radioButtonSdisagree)

        if (rb1.isChecked ||rb2.isChecked ||rb3.isChecked ||rb4.isChecked ||rb5.isChecked ){
            val Id = intent.getIntExtra("id",0)
            var rbGroupA = findViewById<RadioGroup>(R.id.AnswerGroup)
            var answer = rbGroupA.checkedRadioButtonId
            var  answerT = findViewById<RadioButton>(answer)
            val newAnswer = Answer(-1,answerT.text.toString(),-1,Qid)
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addAnswersToQuestionId(newAnswer)
            when(result) {
                1 ->  {
                    //Toast.makeText(this,"", Toast.LENGTH_SHORT).show()
                }
                -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
                -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
                -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
            }
            rbGroupA.clearCheck()
            nextQuestion()

        }else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
        }

    }


}