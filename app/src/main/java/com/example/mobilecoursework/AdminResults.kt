package com.example.mobilecoursework

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mobilecoursework.Model.Question
import com.example.surveyapplication.model.DataBaseHelper
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class AdminResults : AppCompatActivity() {

    lateinit var pieChart: PieChart
    lateinit var questionList : ArrayList<Question>
    lateinit var questionList10 : ArrayList<Question>
    lateinit var currentQuestionid : Question
    var questionCounterTotal :Int = 0
    var Qid :Int = 0
    var questionCounter :Int = 0


    var list = ArrayList<PieEntry>()

    var Sagreecount :Int = 0
    var agreecount :Int = 0
    var Neithercount :Int = 0
    var Disagreecount :Int = 0
    var Sdisagreecount :Int = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_results)

        val Id = intent.getIntExtra("id",0)
        val Title = intent.getStringExtra("Title")
        val StartDate = intent.getStringExtra("StartDate")
        val EndDate = intent.getStringExtra("EndDate")


        var backbtn = findViewById<Button>(R.id.button2)
        backbtn.setOnClickListener {
            val i = Intent(this,AdminHome::class.java)
            startActivity(i)
        }

        val mydatabase = DataBaseHelper(this)

        var surveyTitle = mydatabase.GetSurveyTitleById(Id)
        questionList = mydatabase.getallQuestionsBySurveyId(Id)
        questionList10 = questionList.take(10) as ArrayList<Question>
        questionCounterTotal = questionList10.size
        currentQuestionid = questionList10.get(questionCounter)
        Qid = currentQuestionid.Id









        pieChart=findViewById(R.id.pie_chart)

        Sagreecount = mydatabase.AnswerStatsOfSurveyQuestionStronglyAgree(Qid)
        agreecount =  mydatabase.AnswerStatsOfSurveyQuestionAgree(Qid)
        Neithercount = mydatabase.AnswerStatsOfSurveyQuestionNeither(Qid)
        Disagreecount = mydatabase.AnswerStatsOfSurveyQuestionDisAgree(Qid)
        Sdisagreecount = mydatabase.AnswerStatsOfSurveyQuestionStronglyDisAgree(Qid)


        list.add(PieEntry(Sagreecount.toFloat(),"Strongly Agree"))
        list.add(PieEntry(agreecount.toFloat(),"Agree"))
        list.add(PieEntry(Neithercount.toFloat(),"Neither Agree Nor Disagree"))
        list.add(PieEntry(Disagreecount.toFloat(),"Disagree"))
        list.add(PieEntry(Sdisagreecount.toFloat(),"Strongly Disagree"))

        val pieDataSet= PieDataSet(list,"${currentQuestionid.QuestionText}")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        pieDataSet.valueTextColor= Color.BLACK
        pieDataSet.valueTextSize=15f

        val pieData= PieData(pieDataSet)

        pieChart.data= pieData

        pieChart.description.text= "Pie Chart for $surveyTitle Survey"

        pieChart.centerText="${currentQuestionid.QuestionText}"

        pieChart.animateY(2000)


        questionCounter++
    }


    fun nextBtn(view: View){

        if (questionCounter < questionCounterTotal){
            currentQuestionid = questionList10.get(questionCounter)
            Qid = currentQuestionid.Id

            list.clear()

            val mydatabase = DataBaseHelper(this)
            Sagreecount = mydatabase.AnswerStatsOfSurveyQuestionStronglyAgree(Qid)
            agreecount =  mydatabase.AnswerStatsOfSurveyQuestionAgree(Qid)
            Neithercount = mydatabase.AnswerStatsOfSurveyQuestionNeither(Qid)
            Disagreecount = mydatabase.AnswerStatsOfSurveyQuestionDisAgree(Qid)
            Sdisagreecount = mydatabase.AnswerStatsOfSurveyQuestionStronglyDisAgree(Qid)

            list.add(PieEntry(Sagreecount.toFloat(),"Strongly Agree"))
            list.add(PieEntry(agreecount.toFloat(),"Agree"))
            list.add(PieEntry(Neithercount.toFloat(),"Neither Agree Nor Disagree"))
            list.add(PieEntry(Disagreecount.toFloat(),"Disagree"))
            list.add(PieEntry(Sdisagreecount.toFloat(),"Strongly Disagree"))

            val pieDataSet= PieDataSet(list,"${currentQuestionid.QuestionText}")

            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)

            pieDataSet.valueTextColor= Color.BLACK
            pieDataSet.valueTextSize=15f
            val pieData= PieData(pieDataSet)

            pieChart.data= pieData


            pieChart.centerText="${currentQuestionid.QuestionText}"

            pieChart.animateY(2000)

            questionCounter++
        }
        else{
            var btnNext = findViewById<Button>(R.id.buttonNextPie)


            btnNext.text = "Finish Survey"
            btnNext.setOnClickListener {
                val i = Intent(this,AdminHome::class.java)
                startActivity(i)
            }
        }
    }


}