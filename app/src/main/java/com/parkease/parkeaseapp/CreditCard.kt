package com.parkease.parkeaseapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreditCard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

        val etCardNumber: EditText = findViewById(R.id.et_card_number)
        val etExpiryDate: EditText = findViewById(R.id.et_expiry_date)
        val etCvv: EditText = findViewById(R.id.et_cvv)
        val etCardHolderName: EditText = findViewById(R.id.et_card_holder_name)
        val btnSubmit: Button = findViewById(R.id.btn_submit)

        val total= intent.getIntExtra("TOTAL_AMOUNT", 0)

        btnSubmit.setOnClickListener {
            val cardNumber = etCardNumber.text.toString().trim()
            val expiryDate = etExpiryDate.text.toString().trim()
            val cvv = etCvv.text.toString().trim()
            val cardHolderName = etCardHolderName.text.toString().trim()

            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty() || cardHolderName.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent= Intent(this, BookingConfirmation::class.java)
                intent.putExtra("TOTAL_AMOUNT", total)

                startActivity(intent)
                Toast.makeText(this, "Card Submitted Successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}