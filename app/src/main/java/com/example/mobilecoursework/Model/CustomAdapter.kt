package com.example.mobilecoursework.Model

import android.content.Context
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mobilecoursework.ItemList
import com.example.mobilecoursework.R

class CustomAdapter(private val appContext: Context, private val surveyList: ArrayList<Survey>) : BaseAdapter() {

    private val inflater: LayoutInflater = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

//    ,private var display: ItemList
    override fun getCount(): Int {
        return surveyList.size
    }

    override fun getItem(i: Int): Any? {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        var view: View? = view
        view = inflater.inflate(R.layout.activity_item_list, parent, false)

        val sId = view.findViewById<TextView>(R.id.textViewId)
       val sTitle = view.findViewById<TextView>(R.id.sTitle)
        val sStartDate = view.findViewById<TextView>(R.id.sStart)
        val sEndDate = view.findViewById<TextView>(R.id.sEnd)
        sId.text = surveyList[position].Id.toString()
        sTitle.text = surveyList[position].Title
       sStartDate.text = surveyList[position].StartDate
       sEndDate.text = surveyList[position].EndDate



        return view
    }
}
