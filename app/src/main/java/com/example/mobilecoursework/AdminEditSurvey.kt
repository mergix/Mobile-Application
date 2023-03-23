package com.example.mobilecoursework

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mobilecoursework.Model.Survey
import com.example.surveyapplication.model.DataBaseHelper
import java.util.*

class AdminEditSurvey : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_survey)
        datePopUp()

        val Id = intent.getIntExtra("id",0)
        val Title = intent.getStringExtra("Title")
        val StartDate = intent.getStringExtra("StartDate")
        val EndDate = intent.getStringExtra("EndDate")

        var backbtn = findViewById<Button>(R.id.backAdmin)
        backbtn.setOnClickListener {
            val i = Intent(this,AdminHome::class.java)
            startActivity(i)
        }


        var idSet   =   findViewById<TextView>(R.id.DeleteId)
       var titleSet =  findViewById<EditText>(R.id.editTextSurveyTitle)
        var sDateset = findViewById<TextView>(R.id.textViewStartDate)
        var eDateset = findViewById<TextView>(R.id.textViewEndDate)

        idSet.text = Id.toString()
        titleSet.setText(Title)
        sDateset.text = StartDate
        eDateset.text = EndDate

    }


    fun datePopUp(){
        var dateset = findViewById<TextView>(R.id.textViewStartDate)
        var dateend = findViewById<TextView>(R.id.textViewEndDate)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dateset.setOnClickListener{
            val datePopup = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    if (mDay< 10 && mMonth < 9 ){
                        dateset.text = "" + ("0"+ mDay) + "/" + ( "0" + (mMonth +1))+ "/" + mYear
                    }else if (mMonth < 9 && mDay> 10){
                        dateset.text = "" + mDay + "/" + ( "0" + (mMonth +1))+ "/" + mYear
                    }
                    else if (mDay <10 && mMonth > 9){
                        dateset.text = "" + ("0"+ mDay) + "/" + (mMonth +1)+ "/" + mYear
                    }
            }, year , month , day)
            datePopup.show()
        }
        dateend.setOnClickListener{
            val datePopup = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    if (mDay< 10 && mMonth < 9 ){
                        dateend.text = "" + ("0"+ mDay) + "/" + ( "0" + (mMonth +1))+ "/" + mYear
                    }else if (mMonth < 9 && mDay> 10){
                        dateend.text = "" + mDay + "/" + ( "0" + (mMonth +1))+ "/" + mYear
                    }
                    else if (mDay <10 && mMonth > 9){
                        dateend.text = "" + ("0"+ mDay) + "/" + (mMonth +1)+ "/" + mYear
                    }
            }, year , month , day)
            datePopup.show()
        }

    }

    fun editBtn(view: View){


        var surveyId = findViewById<TextView>(R.id.DeleteId).text.toString().toInt()
        var surveyTitle = findViewById<EditText>(R.id.editTextSurveyTitle).text.toString()
        var surveyStartDate = findViewById<TextView>(R.id.textViewStartDate).text.toString()
        var surveyEndDate = findViewById<TextView>(R.id.textViewEndDate).text.toString()


        val newSurvey = Survey(surveyId,surveyTitle,surveyStartDate,surveyEndDate,-1)
        val mydatabase = DataBaseHelper(this)
        val result = mydatabase.updateSurvey(newSurvey)
        when(result) {
            1 ->  {
                Toast.makeText(this,"Your survey has been edited successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AdminHome::class.java).apply {
                    putExtra(AlarmClock.EXTRA_MESSAGE, surveyId)
                }
                startActivity(intent)
            }
            -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
            -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
            -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
        }
    }

}