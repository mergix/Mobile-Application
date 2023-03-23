package com.example.mobilecoursework.Model

import android.content.Context
import com.example.surveyapplication.model.DataBaseHelper

class ListHub(context: Context) {
//    private val questionList: ArrayList<Question>
    private val surveyList: ArrayList<Survey>
//    private val answerList: ArrayList<Answer>
    private val dbHelper: DataBaseHelper = DataBaseHelper(context)

    init {
//        questionList = dbHelper.getallQuestionsBySurveyId()
        surveyList = dbHelper.getallSurveys()
//        answerList = dbHelper.getallAnswers()
    }

//    fun getQuestionList(): ArrayList<Question>{
//        return questionList
//    }

    fun getSurveyList(): ArrayList<Survey>{
        return surveyList
    }

//    fun getAnswerList(): ArrayList<Answer>{
//        return answerList
//    }
}