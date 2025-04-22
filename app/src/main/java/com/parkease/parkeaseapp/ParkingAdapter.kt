package com.parkease.parkeaseapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class ParkingAdapter(
    private val parkingList: List<ParkingSpot>,
    private val onItemClick: (ParkingSpot) -> Unit
) : RecyclerView.Adapter<ParkingAdapter.ParkingViewHolder>() {

    class ParkingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parkingImageView: CircleImageView = itemView.findViewById(R.id.parkingImageView)
        val parkingNameTextView: TextView = itemView.findViewById(R.id.parkingNameTextView)
        val parkingRatingBar: RatingBar = itemView.findViewById(R.id.parkingRatingBar)
        val parkingInfoTextView: TextView = itemView.findViewById(R.id.parkingInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parking, parent, false)
        return ParkingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParkingViewHolder, position: Int) {
        val currentItem = parkingList[position]

        holder.parkingImageView.setImageResource(currentItem.imageResId)
        holder.parkingNameTextView.text = currentItem.name
        holder.parkingRatingBar.rating = currentItem.rating
        holder.parkingInfoTextView.text = currentItem.info

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    override fun getItemCount() = parkingList.size
}