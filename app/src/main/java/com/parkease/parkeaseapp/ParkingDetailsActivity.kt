package com.parkease.parkeaseapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout

class ParkingDetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_details)

        // Set up toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Get data from intent
        val parkingName = intent.getStringExtra("PARKING_NAME") ?: "Parking Details"
        val parkingRating = intent.getFloatExtra("PARKING_RATING", 0f)
        val parkingImageId = intent.getIntExtra("PARKING_IMAGE", R.drawable.parking3)
        val parkingInfo = intent.getStringExtra("PARKING_INFO") ?: ""
        val parkingLat = intent.getDoubleExtra("PARKING_LAT", 0.0)
        val parkingLng = intent.getDoubleExtra("PARKING_LNG", 0.0)

        // Set title in CollapsingToolbarLayout
        val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.collapsingToolbar)
        collapsingToolbar.title = parkingName

        // Initialize views
        val headerImageView: ImageView = findViewById(R.id.parkingHeaderImageView)
        val nameTextView: TextView = findViewById(R.id.parkingDetailNameTextView)
        val ratingBar: RatingBar = findViewById(R.id.parkingDetailRatingBar)
        val addressTextView: TextView = findViewById(R.id.parkingAddressTextView)
        val availableSpotsTextView: TextView = findViewById(R.id.availableSpotsTextView)
        val priceTextView: TextView = findViewById(R.id.priceTextView)
        val distanceTextView: TextView = findViewById(R.id.distanceTextView)
        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        val bookParkingButton: Button = findViewById(R.id.bookParkingButton)

        // Set view content
        headerImageView.setImageResource(parkingImageId)
        nameTextView.text = parkingName
        ratingBar.rating = parkingRating

        // Extract distance from parkingInfo (assuming format like "1.2 km away")
        addressTextView.text = "Address: $parkingName Location"
        distanceTextView.text = parkingInfo

        // Set default values for other fields
        availableSpotsTextView.text = "${(10..50).random()}"  // Random number between 10-50
        priceTextView.text = "₹${(20..50).random()}"          // Random price between ₹20-₹50

        // Default description
        descriptionTextView.text = "This $parkingName offers secure and convenient parking spaces. " +
                "It is well-lit, monitored 24/7, and easily accessible from main roads."

        // Set up button click listener
        bookParkingButton.setOnClickListener {
            val intent = Intent(this, SeatActivity::class.java)
            startActivity(intent)
        }
    }

    // Handle back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}