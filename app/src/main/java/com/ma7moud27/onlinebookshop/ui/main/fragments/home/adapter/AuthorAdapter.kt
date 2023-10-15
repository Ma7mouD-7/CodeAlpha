package com.ma7moud27.onlinebookshop.ui.main.fragments.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.utils.UtilMethods
import com.ma7moud27.onlinebookshop.utils.enums.CoverSize

class AuthorAdapter(
    private var authorList: List<Author>,
    private val listener: OnAuthorItemClickListener,
    private val context: Context,
) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder =
        AuthorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_author, parent, false),
        )

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onAuthorItemClick(position) }

        holder.authorNameTextView.text = authorList[position].name
        Glide.with(context)
            .asBitmap()
            .load(UtilMethods.createAuthorPicUrl(authorList[position].key ?: "", size = CoverSize.MEDIUM.query))
            .into(holder.authorPicImageView)
    }

    override fun getItemCount(): Int = authorList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(authorList: List<Author>) {
        this.authorList = authorList
        notifyDataSetChanged()
    }

    class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorNameTextView: TextView
        val authorPicImageView: ImageView
        init {
            authorNameTextView = itemView.findViewById(R.id.home_author_name_tv)
            authorPicImageView = itemView.findViewById(R.id.home_author_pic_iv)
        }
    }
}

interface OnAuthorItemClickListener {
    fun onAuthorItemClick(position: Int)
}
