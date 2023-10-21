package com.ma7moud27.shelfy.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.utils.enums.Category

class CategoryListAdapter(
    private var itemsList: List<Category>,
    private val listener: OnCategoryListClickListener,
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false),
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onCategoryListClick(position) }

        holder.categoryName.text = itemsList[position].name.replace("_", " ")
        holder.categoryDescription.text = itemsList[position].description
        holder.categoryCover.setImageResource(itemsList[position].icon)
    }

    override fun getItemCount(): Int = itemsList.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView
        var categoryDescription: TextView
        var categoryCover: ImageView

        init {
            categoryName = itemView.findViewById(R.id.item_category_list_name_tv)
            categoryDescription = itemView.findViewById(R.id.item_category_list_desc_tv)
            categoryCover = itemView.findViewById(R.id.item_category_list_cover_iv)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(itemsList: List<Category>) {
        this.itemsList = itemsList
        notifyDataSetChanged()
    }
}

fun interface OnCategoryListClickListener {
    fun onCategoryListClick(position: Int)
}
