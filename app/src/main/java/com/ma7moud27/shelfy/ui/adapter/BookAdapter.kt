package com.ma7moud27.shelfy.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ma7moud27.shelfy.R
import com.ma7moud27.shelfy.model.SearchBookItem
import com.ma7moud27.shelfy.utils.UtilMethods
import com.ma7moud27.shelfy.utils.enums.CoverKey
import com.ma7moud27.shelfy.utils.enums.CoverSize

class BookAdapter(
    private var bookItems: List<SearchBookItem>,
    private val listener: OnBookItemClickListener,
    private val context: Context,
    private val itemBook: Int,
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(itemBook, parent, false),
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onBookItemClick(holder, position) }

        bookItems[position].publishYear.apply {
            if (this == null) {
                "${bookItems[position].title}".also { holder.titleTextView.text = it }
            } else {
                "${bookItems[position].title} (${bookItems[position].publishYear})".also { holder.titleTextView.text = it }
            }
        }

        holder.authorTextView.text = bookItems[position].authorName!!.joinToString(", ")

        Glide.with(context)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(
                bookItems[position].let {
                    if (it.lendingEditionKey.isNullOrEmpty()) {
                        UtilMethods.createCoverUrl(
                            it.coverID.toString(),
                            CoverKey.ID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
                    } else {
                        UtilMethods.createCoverUrl(
                            it.lendingEditionKey,
                            CoverKey.OLID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
                    }
                },
            )
            .into(holder.coverImageView)

    }

    override fun getItemCount(): Int = bookItems.size

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView
        val authorTextView: TextView
        val coverImageView: ImageView

        init {
            titleTextView = itemView.findViewById(R.id.item_book_title_tv)
            authorTextView = itemView.findViewById(R.id.item_book_author_tv)
            coverImageView = itemView.findViewById(R.id.item_book_cover_iv)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(bookItems: List<SearchBookItem>) {
        this.bookItems = bookItems
        notifyDataSetChanged()
    }
}

fun interface OnBookItemClickListener {
    fun onBookItemClick(holder: BookAdapter.BookViewHolder, position: Int)
}
