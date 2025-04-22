package com.parkease.parkeaseapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrUpiPayment : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_upi_payment)

        val qrImageView = findViewById<ImageView>(R.id.qrImageView)
        val btnProceed = findViewById<Button>(R.id.btnProceedPayment)

        // Generate QR Code with UPI Payment Link
        val upiUri = "upi://pay?pa=merchant@upi&pn=Merchant Name&mc=1234&tid=00001&tr=123456789&tn=Payment&am=100.00&cu=INR"
        generateQRCode(upiUri, qrImageView)

        // Handle Button Click for UPI Payment
        btnProceed.setOnClickListener {
            initiateUPIPayment(upiUri)
            Toast.makeText(this, "payment succesfully done", Toast.LENGTH_LONG).show()
        }
    }

    private fun generateQRCode(data: String, imageView: ImageView) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error generating QR Code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initiateUPIPayment(upiUri: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(upiUri)
            intent.setPackage("com.google.android.apps.nbu.paisa.user") // Google Pay
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show()
        }
    }
}