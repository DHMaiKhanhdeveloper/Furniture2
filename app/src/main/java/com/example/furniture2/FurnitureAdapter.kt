package com.example.furniture2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_model.view.*

class FurnitureAdapter(
    private var furnitures: List<Furniture>,
    private val itemClick: (Furniture) -> Unit
) : RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>() {

    private var selectedFurniturelIndex = 0
    var selectedFurniture = MutableLiveData<Furniture>()

    class FurnitureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model, parent, false)

        return FurnitureViewHolder(view)
    }

    override fun getItemCount() = furnitures.size

    fun submitFurnitures(furnitures: List<Furniture>) {
        this.furnitures = furnitures
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {

        if (selectedFurniturelIndex == holder.layoutPosition) {
            holder.itemView.setBackgroundColor(SELECTED_MODEL_COLOR)
            selectedFurniture.value = furnitures[holder.layoutPosition]
        } else {
            holder.itemView.setBackgroundColor(UNSELECTED_MODEL_COLOR)
        }


        holder.itemView.apply {
            ivThumbnail.setImageResource(furnitures[position].imageFurniture)
            tvTitle.text = furnitures[position].titleFurniture

            setOnClickListener {
                selectedFurniture(holder)
                itemClick(furnitures[position])
            }
        }
    }

    private fun selectedFurniture(holder: FurnitureViewHolder) {
        if (selectedFurniturelIndex != holder.layoutPosition) {
            holder.itemView.setBackgroundColor(SELECTED_MODEL_COLOR)
            notifyItemChanged(selectedFurniturelIndex)
            selectedFurniturelIndex = holder.layoutPosition
            selectedFurniture.value = furnitures[holder.layoutPosition]
        }
    }

    companion object {
        const val SELECTED_MODEL_COLOR = Color.YELLOW
        const val UNSELECTED_MODEL_COLOR = Color.LTGRAY
    }

}