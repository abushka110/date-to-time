package com.example.agetominutescalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvMinToFromDate : TextView? = null
    private var tvAgeInHours : TextView? = null
    private var tvHoursToFromDate : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.textViewSelectedDate)
        tvAgeInMinutes = findViewById(R.id.textViewAgeInMinutes)
        tvMinToFromDate = findViewById(R.id.minToFromDate)
        tvAgeInHours = findViewById(R.id.textViewAgeInHours)
        tvHoursToFromDate = findViewById(R.id.hoursToFromDate)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {

        val appCalendar = Calendar.getInstance()
        val year = appCalendar.get(Calendar.YEAR)
        val month = appCalendar.get(Calendar.MONTH)
        val day = appCalendar.get(Calendar.DATE)
        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this,
                    "Year was $selectedYear, month was ${selectedMonth+1} and day was $selectedDayOfMonth",
                    Toast.LENGTH_LONG
                ).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                // for date to minutes
                val selectedDateInMinutes = theDate.time / 60000
                val currentDateInMinutes =  currentDate.time / 60000

                // for date to hours
                val selectedDateInHours = theDate.time / 3600000
                val currentDateInHours =  currentDate.time / 3600000

                var differenceInMinutes : Long = 0
                var differenceInHours : Long = 0

                if (currentDateInMinutes < selectedDateInMinutes) {
                    tvMinToFromDate?.text = "in minutes to date"
                    differenceInMinutes = selectedDateInMinutes - currentDateInMinutes

                    tvHoursToFromDate?.text = "in hours to date"
                    differenceInHours = selectedDateInHours - currentDateInHours
                } else {
                    tvMinToFromDate?.text = "in minutes from date"
                    differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                    tvHoursToFromDate?.text = "in hours from date"
                    differenceInHours = currentDateInHours - selectedDateInHours
                }
                tvAgeInMinutes?.text = differenceInMinutes.toString()
                tvAgeInHours?.text = differenceInHours.toString()
            },
            year,
            month,
            day,
        )
        dpd.show()
    }
}