package com.example.mobilecoursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.example.mobilecoursework.Model.Admin
import com.example.mobilecoursework.Model.Student
import com.example.surveyapplication.model.DataBaseHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loginButton(view: View) {
        val userName = findViewById<EditText>(R.id.editTextLoginName).text.toString()
        val userPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()
        val switchA = findViewById<Switch>(R.id.switchAdminLogin)

        if (switchA.isChecked)
            if (userName.isEmpty() || userPassword.isEmpty())
                Toast.makeText(this, "Please insert Username and Password", Toast.LENGTH_LONG).show()
            else {
                val myDataBase = DataBaseHelper(this)
                val result = myDataBase.getAdmin(
                    Admin(
                        -1,userName, userPassword
                    )
                )
                if (result == -1)
                    Toast.makeText(this, "Admin Not Found, Please try again", Toast.LENGTH_LONG).show()
                else if (result == -2)
                    Toast.makeText(this, "Error Cannot Open DataBase", Toast.LENGTH_LONG).show()
                else {
                    Toast.makeText(this, "You logged in successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AdminHome::class.java)
                    startActivity(intent)
                }
            }
            else {
            if (userName.isEmpty() || userPassword.isEmpty())
                Toast.makeText(this, "Please insert Username and Password", Toast.LENGTH_LONG).show()
            else {
                val myDataBase = DataBaseHelper(this)
                val result = myDataBase.getStudent(
                    Student(
                        -1, userName, userPassword
                    )
                )
                if (result == -1)
                    Toast.makeText(this, "User Not Found, Please try again", Toast.LENGTH_LONG).show()
                else if (result == -2)
                    Toast.makeText(this, "Error Cannot Open DataBase", Toast.LENGTH_LONG).show()
                else {
                    Toast.makeText(this, "You logged in successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, StudentHome::class.java)
                    startActivity(intent)
                }
            }
        }

}
    fun registerButton(view: View) {
            val intent = Intent(this, NewAccount::class.java)
            startActivity(intent)
        }
    }
