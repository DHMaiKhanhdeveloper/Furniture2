package com.example.furniture2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_model.view.*

class CategoryAdapter(
    private val categories: List<Category>,
    private val itemClick: (Category) -> Unit

) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var selectedModel = MutableLiveData<Category>()
    private var selectedModelIndex = 0

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount() = categories.size


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        if (selectedModelIndex == holder.layoutPosition) {
            holder.itemView.setBackgroundColor(SELECTED_MODEL_COLOR)
            selectedModel.value = categories[holder.layoutPosition]
        } else {
            holder.itemView.setBackgroundColor(UNSELECTED_MODEL_COLOR)
        }



        holder.itemView.apply {
            ivThumbnail.setImageResource(categories[position].imageResource)
            tvTitle.text = categories[position].title

            setOnClickListener {
                selectModel(holder)


            }
        }
        holder.itemView.setOnClickListener {
            itemClick(categories[position])
        }
    }

    private fun selectModel(holder: CategoryViewHolder) {
        if (selectedModelIndex != holder.layoutPosition) {
            holder.itemView.setBackgroundColor(SELECTED_MODEL_COLOR)
            notifyItemChanged(selectedModelIndex)
            selectedModelIndex = holder.layoutPosition
            selectedModel.value = categories[holder.layoutPosition]
        }
    }

    companion object {
        const val SELECTED_MODEL_COLOR = Color.YELLOW
        const val UNSELECTED_MODEL_COLOR = Color.LTGRAY
    }
}