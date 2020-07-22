package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var format = arrayOf("Minutes", "Hours", "Days")
    var position_value : Int=0;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, format
        )

        spinner.adapter = adapter;
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                position_value=position
                Toast.makeText(applicationContext, "Selected Format:"+ format[position], Toast.LENGTH_SHORT).show()
                if (!tvSelectedDate.text.isEmpty()) {
                    calculateAge(format[position])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        btnDatePicker.setOnClickListener { view ->
            clickDataPicker(view)
        }

    }

    private fun clickDataPicker(view: View?) {
        val c = Calendar.getInstance()
        val year =c.get(Calendar.YEAR) // Returns the value of the given calendar field. This indicates YEAR
        val month = c.get(Calendar.MONTH) // This indicates the Month
        val day = c.get(Calendar.DAY_OF_MONTH) // This indicates the Day
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                tvSelectedDate.setText(selectedDate)
                calculateAge(format[spinner.selectedItemPosition])
            },year,month,day)
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show() // It is used to show the datePicker Dialog.
    }

    private fun calculateAge(s: String) {

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val selectedDate = tvSelectedDate.text.toString();
            val theDate = sdf.parse(selectedDate)
            val selectedDateToMinutes = theDate!!.time / 60000
            sdf.format(Date())
            val currentDate = sdf.parse(sdf.format(Date()))
            val currentDateToMinutes = currentDate!!.time / 60000
            var differenceInMinutes = currentDateToMinutes - selectedDateToMinutes

            if(s.equals("Minutes",true)){
                tvSelectedDateInMinutes.setText(differenceInMinutes.toString())
                tvFormatOfYourAge.setText("In minutes till date")
            }else if(s.equals("Hours",true)){
                var differenceInhour=differenceInMinutes/60
                tvSelectedDateInMinutes.setText(differenceInhour.toString())
                tvFormatOfYourAge.setText("In Hours till date")
            }else if(s.equals("Days",true)){
                var differenceInDays=differenceInMinutes/1140
                tvSelectedDateInMinutes.setText(differenceInDays.toString())
                tvFormatOfYourAge.setText("In Days till date")
            }


    }
}



