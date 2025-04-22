package com.parkease.parkeaseapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class BookingConfirmation : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_confirmation)

        val returnhome= findViewById<Button>(R.id.btnHome)
        val bookingAmount= findViewById<TextView>(R.id.amountdetail)

        val tot= intent.getIntExtra("TOTAL_AMOUNT", 0)

        bookingAmount.text= "Amount to Pay: ₹${tot}/-"


        returnhome.setOnClickListener {

            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)



        }

    }
}