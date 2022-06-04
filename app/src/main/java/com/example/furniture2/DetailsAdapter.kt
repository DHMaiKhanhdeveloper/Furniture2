package com.example.furniture2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_details.view.*

class DetailsAdapter(
    private var details: List<Detail>,
    private val itemClick: (Detail) -> Unit
) : RecyclerView.Adapter<DetailsAdapter.DetailViewHolder>() {

    fun submitDetails(details: List<Detail>) {
        this.details = details
        notifyDataSetChanged()
    }

    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_details, parent, false)

        return DetailViewHolder(view)
    }

    override fun getItemCount() = details.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.itemView.apply {
            ivDetails.setImageResource(details[position].imageDetails)
            tvTitleDetails.text = details[position].titleDetails
        }
        holder.itemView.setOnClickListener {
            itemClick(details[position])
        }
    }
}