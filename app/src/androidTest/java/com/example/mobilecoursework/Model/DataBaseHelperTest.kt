package com.example.mobilecoursework.Model

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.surveyapplication.model.DataBaseHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DataBaseHelperTest{


    var id = 1
    var text = "CTEC3911"
//    @Before
//    public override fun setUp(){
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val mydatabase = DataBaseHelper(context)
//        val result = mydatabase.GetSurveyTitleById(id)
//    }

    @Test
    fun gettitleByID() = runBlocking {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val mydatabase = DataBaseHelper(context)
        val result = mydatabase.GetSurveyTitleById(id)

        assertEquals(text , result)
    }

}