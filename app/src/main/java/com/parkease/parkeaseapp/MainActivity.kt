package com.parkease.parkeaseapp


import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.Intent
import android.text.TextWatcher
import android.text.Editable
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.gms.maps.model.MapStyleOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var searchEditText: EditText
    private lateinit var parkingRecyclerView: RecyclerView
    private lateinit var parkingAdapter: ParkingAdapter

    // Define locations for different  cities
    private val jalandharLocation = LatLng(31.3260, 75.5762)
    private val phagwaraLocation = LatLng(31.2240, 75.7712)
    private val currentLocation = LatLng(31.2560, 75.7012) // Example "current" location

    // Sample parking data for each location
    private val jalandharParkings = listOf(
        ParkingSpot("Golden Mall Parking", 4.3f, R.drawable.parking3, "1.2 km away", jalandharLocation),
        ParkingSpot("MBD Neopolis Parking", 3.9f, R.drawable.parking3, "2.4 km away", jalandharLocation),
        ParkingSpot("City Center Parking", 4.5f, R.drawable.parking3, "0.8 km away", jalandharLocation),
        ParkingSpot("Jyoti Chowk Parking", 3.7f, R.drawable.parking3, "3.1 km away", jalandharLocation),
        ParkingSpot("Model Town Parking", 4.1f, R.drawable.parking3, "1.9 km away", jalandharLocation)
    )

    private val phagwaraParkings = listOf(
        ParkingSpot("Satnampura Parking", 4.0f, R.drawable.parking3, "0.7 km away", phagwaraLocation),
        ParkingSpot("Hargobind Nagar Parking", 3.8f, R.drawable.parking3, "1.5 km away", phagwaraLocation),
        ParkingSpot("Chahal Nagar Parking", 4.2f, R.drawable.parking3, "2.2 km away", phagwaraLocation),
        ParkingSpot("Railway Road Parking", 3.6f, R.drawable.parking3, "3.0 km away", phagwaraLocation),
        ParkingSpot("Bus Stand Parking", 4.4f, R.drawable.parking3, "0.5 km away", phagwaraLocation)
    )

    private val nearbyParkings = listOf(
        ParkingSpot("Central Parking", 4.2f, R.drawable.parking3, "0.3 km away", currentLocation),
        ParkingSpot("Silver Mall Parking", 3.9f, R.drawable.parking3, "0.6 km away", currentLocation),
        ParkingSpot("Metro Parking", 4.5f, R.drawable.parking3, "1.0 km away", currentLocation),
        ParkingSpot("Grand Plaza Parking", 4.1f, R.drawable.parking3, "1.4 km away", currentLocation),
        ParkingSpot("City View Parking", 3.7f, R.drawable.parking3, "1.8 km away", currentLocation)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        searchEditText = findViewById(R.id.searchEditText)
        parkingRecyclerView = findViewById(R.id.parkingRecyclerView)

        // Setup map fragment
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Setup RecyclerView with empty adapter initially
        setupRecyclerView(emptyList())

        // Setup search functionality
        setupSearch()
    }

    private fun setupRecyclerView(parkings: List<ParkingSpot>) {
        parkingRecyclerView.layoutManager = LinearLayoutManager(this)
        parkingAdapter = ParkingAdapter(parkings) { parkingSpot ->
            // Handle parking item click - navigate to details
            val intent = Intent(this, ParkingDetailsActivity::class.java)
            intent.putExtra("PARKING_NAME", parkingSpot.name)
            intent.putExtra("PARKING_RATING", parkingSpot.rating)
            intent.putExtra("PARKING_IMAGE", parkingSpot.imageResId)
            intent.putExtra("PARKING_INFO", parkingSpot.info)
            intent.putExtra("PARKING_LAT", parkingSpot.location.latitude)
            intent.putExtra("PARKING_LNG", parkingSpot.location.longitude)
            startActivity(intent)
        }
        parkingRecyclerView.adapter = parkingAdapter
    }

    private fun setupSearch() {
        val searchOptions = listOf(
            "parking near me",
            "parking in jalandhar",
            "parking in phagwara"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, searchOptions)
        val autoCompleteTextView = searchEditText as AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selected = parent.getItemAtPosition(position).toString()
            performSearch(selected)
        }

        // Prevent manual typing and enforce selection only
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }

        autoCompleteTextView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) autoCompleteTextView.showDropDown()
        }
    }


    private fun performSearch(query: String) {
        when (query.lowercase()) {
            "parking near me" -> {
                updateMap(currentLocation, "Nearby Parkings")
                setupRecyclerView(nearbyParkings)
            }
            "parking in jalandhar" -> {
                updateMap(jalandharLocation, "Jalandhar Parkings")
                setupRecyclerView(jalandharParkings)
            }
            "parking in phagwara" -> {
                updateMap(phagwaraLocation, "Phagwara Parkings")
                setupRecyclerView(phagwaraParkings)
            }
            else -> {
                Toast.makeText(this, "Try searching: parking near me, parking in jalandhar, or parking in phagwara", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateMap(location: LatLng, title: String) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(location).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))

        // Add markers for each parking location in the current view
        val currentParkings = when(title) {
            "Jalandhar Parkings" -> jalandharParkings
            "Phagwara Parkings" -> phagwaraParkings
            else -> nearbyParkings
        }

        for (parking in currentParkings) {
            mMap.addMarker(MarkerOptions().position(parking.location).title(parking.name))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Apply custom styling to the map
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            )
            if (!success) {
                // Style parsing failed
            }
        } catch (e: Exception) {
            // Couldn't load style
        }

        // Set default location
        val defaultLocation = LatLng(31.3260, 75.5762) // Jalandhar
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))
    }
}