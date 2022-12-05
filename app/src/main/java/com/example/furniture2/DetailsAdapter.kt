package com.example.furniture2

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_details.view.*

class DetailsAdapter(
    private var details: List<Detail>,
    private val itemClick: (Detail) -> Unit
) : RecyclerView.Adapter<DetailsAdapter.DetailViewHolder>()

{

    private var selectedDetailIndex = 0
    var selectedDetail = MutableLiveData<Detail>()

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
        if (selectedDetailIndex == holder.layoutPosition) {
            holder.itemView.setBackgroundColor(SELECTED_MODEL_COLOR)
            selectedDetail.value = details[holder.layoutPosition]
        } else {
            holder.itemView.setBackgroundColor(UNSELECTED_MODEL_COLOR)
        }

        holder.itemView.apply {
            ivDetails.setImageResource(details[position].imageDetails)
            tvTitleDetails.text = details[position].titleDetails

            setOnClickListener {
                selectDetail(holder)
                itemClick(details[position])
            }

        }
    }

    private fun selectDetail(holder: DetailViewHolder) {
        if (selectedDetailIndex != holder.layoutPosition) {
            holder.itemView.setBackgroundColor(SELECTED_MODEL_COLOR)
            notifyItemChanged(selectedDetailIndex)
            selectedDetailIndex = holder.layoutPosition
            selectedDetail.value = details[holder.layoutPosition]
        }
    }

    companion object {
        const val SELECTED_MODEL_COLOR = Color.YELLOW
        const val UNSELECTED_MODEL_COLOR = Color.LTGRAY
    }
}