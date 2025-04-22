package com.parkease.parkeaseapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val upi= findViewById<LinearLayout>(R.id.upiLayout)
        val cash= findViewById<LinearLayout>(R.id.cashLayout)
        val totalamount= findViewById<TextView>(R.id.finalamount)
        val debit= findViewById<LinearLayout>(R.id.cardLayout)

        val total= intent.getIntExtra("TOTAL_AMOUNT", 0)

        totalamount.text= "Amount to Pay: ₹${total}/-"

        upi.setOnClickListener{

            val intent= Intent(this, QrUpiPayment::class.java)
            startActivity(intent)
        }

        cash.setOnClickListener{

            val intent= Intent(this, BookingConfirmation::class.java)
            intent.putExtra("TOTAL_AMOUNT", total)
            startActivity(intent)
        }

        debit.setOnClickListener{

            val intent= Intent(this, CreditCard::class.java)
            intent.putExtra("TOTAL_AMOUNT", total)
            startActivity(intent)
        }


    }
}