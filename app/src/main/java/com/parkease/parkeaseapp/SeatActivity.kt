package com.parkease.parkeaseapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SeatActivity : AppCompatActivity() {

    private lateinit var card1: CardView
    private lateinit var changeColorButton: Button
    private lateinit var proceedToPayButton: Button
    private lateinit var amountTextView: TextView
    private var selectedCard: CardView? = null // Variable to hold the currently selected card
    private var previousCard: CardView? = null // Variable to hold the previously selected card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat)

        card1 = findViewById(R.id.card1)
        changeColorButton = findViewById(R.id.changeColorButton)
        proceedToPayButton = findViewById(R.id.proceed_to_pay)
        amountTextView = findViewById(R.id.amount)

        changeColorButton.setOnClickListener {
            if (selectedCard == null) {
                // Show a Toast message if no card is selected
                Toast.makeText(this, "Please select a card.", Toast.LENGTH_SHORT).show()
            } else {
                changeSelectedCardColor()
                // Count red cards and update amount
                updateAmountText()
            }
        }

        proceedToPayButton.setOnClickListener {
            // Count how many cards are red (selected)
            var selectedSeatsCount = 0
            for (i in 1..16) {
                val cardId = resources.getIdentifier("card$i", "id", packageName)
                val cardView: CardView? = findViewById(cardId)
                if (cardView?.cardBackgroundColor?.defaultColor == Color.RED) {
                    selectedSeatsCount++
                }
            }

            if (selectedSeatsCount == 0) {
                // Show error message if no seats are selected
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculate total amount (35 per seat)
            val totalAmount = selectedSeatsCount * 35

            // Create intent and pass the amount
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("TOTAL_AMOUNT", totalAmount)
            startActivity(intent)

        }

        // Set click listeners for each card in the grid
        setCardClickListeners()
    }

    private fun updateAmountText() {
        // Count how many cards are red
        var redCardsCount = 0
        for (i in 1..16) {
            val cardId = resources.getIdentifier("card$i", "id", packageName)
            val cardView: CardView? = findViewById(cardId)
            if (cardView?.cardBackgroundColor?.defaultColor == Color.RED) {
                redCardsCount++
            }
        }
        // Update the amount text based on number of red cards
        val totalAmount = redCardsCount * 35
        amountTextView.text = "Amount ${totalAmount}/-"
    }

    private fun changeSelectedCardColor() {
        // Change the background color of the selected card to red
        selectedCard?.setCardBackgroundColor(Color.RED)
    }

    private fun setCardClickListeners() {
        // Assuming you have 16 cards, you can set click listeners for each one
        for (i in 1..16) {
            val cardId = resources.getIdentifier("card$i", "id", packageName)
            val cardView: CardView? = findViewById(cardId)

            cardView?.setOnClickListener {
                // Check if the card is already red
                if (cardView.cardBackgroundColor.defaultColor != Color.RED) {
                    // Reset the previous card's color to white if it exists and is not red
                    previousCard?.let {
                        if (it.cardBackgroundColor.defaultColor != Color.RED) {
                            it.setCardBackgroundColor(Color.WHITE)
                        }
                    }

                    // Set the selected card to the clicked card
                    selectedCard = cardView

                    // Change the color of the currently selected card to blue
                    cardView.setCardBackgroundColor(Color.BLUE)

                    // Update the previous card to the currently selected card
                    previousCard = cardView
                }
            }
        }
    }
}