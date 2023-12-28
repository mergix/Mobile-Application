package com.example.mobilecoursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.mobilecoursework.Model.Admin
import com.example.mobilecoursework.Model.Student
import com.example.surveyapplication.model.DataBaseHelper

class NewAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)
        var switchA = findViewById<Switch>(R.id.AdminRegisterSwitch)
//        switchA.setClickable(false)
    }

    fun registerUser(view: View){
        val userName  = findViewById<EditText>(R.id.editATextLoginName).text.toString()
        val userPassword  = findViewById<EditText>(R.id.editATextPassword).text.toString()
        val switchA = findViewById<Switch>(R.id.AdminRegisterSwitch)

        if (switchA.isChecked)
            if(userName.isEmpty() || userPassword.isEmpty() ) // // User name and password are required
                Toast.makeText(this,"User name and Password are required!", Toast.LENGTH_LONG).show()
            else{
                val newAdmin = Admin(-1,userName,userPassword)
                val mydatabase = DataBaseHelper(this)
                val result = mydatabase.addAdmin(newAdmin)
                when(result){
                    1 ->  {
                        Toast.makeText(this,"Your details has been add to the database successfully",
                            Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        findViewById<Button>(R.id.buttonAdminSignup).isEnabled = false
                    }
                    -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
                    -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
                    -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
                }
            }
        else {
            if(userName.isEmpty() || userPassword.isEmpty() ) // // User name and password are required
                Toast.makeText(this,"User name and Password are required!", Toast.LENGTH_LONG).show()
            //   message.text = "User name and Password are required!"
            else { // Save data
                // Create object
                val newUser = Student(-1,userName,userPassword)
                val mydatabase = DataBaseHelper(this)
                val result = mydatabase.addStudent(newUser)
                when(result) {
                    1 ->  {
                        Toast.makeText(this,"Your details has been add to the database successfully",
                            Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        findViewById<Button>(R.id.buttonAdminSignup).isEnabled = false
                    }
                    -1 -> Toast.makeText(this,"Error on creating new account", Toast.LENGTH_LONG).show()
                    -2 -> Toast.makeText(this,"Error can not open database", Toast.LENGTH_LONG).show()
                    -3 -> Toast.makeText(this,"User name is already exist", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}