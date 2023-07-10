package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null   // proměnnná pro pozdější deklaraci
    private var tvAgeInMinutes : TextView? = null   // proměnnná pro pozdější deklaraci

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)  // přístup k text. poli
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)  // přístup k text. poli

        btnDatePicker.setOnClickListener{   // při kliku na button se spustí tato funkce
            clickDatePicker()
        }
    }
    private fun clickDatePicker(){
        val calendar = Calendar.getInstance()   // získání aktuálního data a času ze systému
        val year = calendar.get(Calendar.YEAR)  // z instance Calendar získáme rok
        val month = calendar.get(Calendar.MONTH)    // -||- měsíc
        val day = calendar.get(Calendar.DAY_OF_MONTH) // -||- den
        val dpd = DatePickerDialog(this,    // okno kde vybíráme datum
            {_, selectedYear, selectedMonth, selectedDayOfMonth ->  // dále se provede kod po výběru data v okně
                Toast.makeText(this, "Rok je $selectedYear, měsíc je ${selectedMonth+1}, den je $selectedDayOfMonth", Toast.LENGTH_LONG).show()  // Vytváříme vyskakovací okénko s informacemi o vybraném datumu

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"  // vybrané datum
                tvSelectedDate?.text = selectedDate  // pokud bude null -> neprovede se, přiřazení vybraného data do proměnné

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)  // vytváříme vzor formátu data dle EN lokalizace
                val theDate = sdf.parse(selectedDate)

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }

                }

            },
            year,
            month,
            day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
            dpd.show()

    }
}