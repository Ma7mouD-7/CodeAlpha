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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.model.SearchBookItem
import com.ma7moud27.onlinebookshop.utils.UtilMethods
import com.ma7moud27.onlinebookshop.utils.enums.CoverKey
import com.ma7moud27.onlinebookshop.utils.enums.CoverSize

class BookAdapter(
    private var bookItems: List<SearchBookItem>,
    private val listener: OnBookItemClickListener,
    private val context: Context,
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false),
        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener.onBookItemClick(position) }

        holder.titleTextView.text =
            "${bookItems[position].title} (${bookItems[position].publishYear})"
        holder.authorTextView.text = bookItems[position].authorName.joinToString(", ")

        Glide.with(context)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(
                bookItems[position].let {
                    if (it.lendingEditionKey != "") {
                        UtilMethods.createCoverUrl(
                            it.lendingEditionKey,
                            CoverKey.OLID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
                    } else {
                        UtilMethods.createCoverUrl(
                            it.coverID.toString(),
                            CoverKey.ID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
                    }
                },
            )
            .into(holder.coverImageView)

        /*object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    val palette = createPaletteSync(bitmap)
                    val dominantColor = palette.getDominantColor(0)
                    imageView.setBackgroundColor(dominantColor)
                }
            }*/

        /*import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

// Function to apply blur effect and set it as background
fun loadImageWithBlur(context: Context, imageUrl: String, imageView: ImageView, radius: Float) {
    val requestOptions = RequestOptions().transform(BlurTransformation(radius))

    Glide.with(context)
        .asBitmap()
        .load(imageUrl)
        .apply(requestOptions)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val blurredBitmap = blurBitmap(context, resource, radius)
                val drawable = BitmapDrawable(context.resources, blurredBitmap)
                imageView.background = drawable
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // Not required for this example
            }
        })
}

// Custom Glide transformation for blur effect
class BlurTransformation(private val radius: Float) : Transformation<Bitmap> {
    override fun transform(context: Context, resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> {
        val blurredBitmap = blurBitmap(context, resource.get(), radius)
        return BitmapResource.obtain(blurredBitmap, resource.bitmapPool)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        // Not required for this example
    }

    private fun blurBitmap(context: Context, bitmap: Bitmap, radius: Float): Bitmap {
        val width = Math.round(bitmap.width * 0.4f)
        val height = Math.round(bitmap.height * 0.4f)
        val inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val allocationIn = Allocation.createFromBitmap(rs, inputBitmap)
        val allocationOut = Allocation.createFromBitmap(rs, outputBitmap)

        blurScript.setRadius(radius)
        blurScript.setInput(allocationIn)
        blurScript.forEach(allocationOut)

        allocationOut.copyTo(outputBitmap)
        rs.destroy()

        return outputBitmap
    }
}*/
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

interface OnBookItemClickListener {
    fun onBookItemClick(position: Int)
}
