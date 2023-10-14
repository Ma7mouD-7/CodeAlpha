package com.ma7moud27.onlinebookshop.ui.main.fragments.home.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.utils.enums.Category

class CategoryAdapter(
    private var itemsList: List<Category>,
    private val listener: OnCategoryItemClickListener,
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false),
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onCategoryItemClick(position) }

        holder.categoryCover.layoutParams.width =
            (holder.categoryCover.layoutParams.width * itemsList[position].width).toInt()
        holder.categoryCover.layoutParams.height =
            (holder.categoryCover.layoutParams.height * itemsList[position].height).toInt()
        holder.categoryName.text =
            itemsList[position].name.replace("_", " ").map { it }.joinToString("\n")
        holder.categoryCover.setBackgroundColor(itemsList[position].color)
        holder.categoryCover.imageTintList = ColorStateList.valueOf(itemsList[position].color)
    }

    override fun getItemCount(): Int = itemsList.size
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView
        var categoryCover: ImageView

        init {
            categoryName = itemView.findViewById(R.id.item_category_name_tv)
            categoryCover = itemView.findViewById(R.id.item_category_cover_iv)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(itemsList: List<Category>) {
        this.itemsList = itemsList
        notifyDataSetChanged()
    }
}

fun interface OnCategoryItemClickListener {
    fun onCategoryItemClick(position: Int)
}
