package com.ma7moud27.onlinebookshop.ui.work.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.ui.work.repository.WorkRepoImpl
import com.ma7moud27.onlinebookshop.ui.work.viewmodel.WorkViewModel
import com.ma7moud27.onlinebookshop.ui.work.viewmodel.WorkViewModelFactory
import com.ma7moud27.onlinebookshop.utils.Constants
import com.ma7moud27.onlinebookshop.utils.UtilMethods
import com.ma7moud27.onlinebookshop.utils.UtilMethods.Companion.clearSources
import com.ma7moud27.onlinebookshop.utils.UtilMethods.Companion.extractIdFromKey
import com.ma7moud27.onlinebookshop.utils.enums.Category
import com.ma7moud27.onlinebookshop.utils.enums.CoverKey
import com.ma7moud27.onlinebookshop.utils.enums.CoverSize
import jp.wasabeef.glide.transformations.BlurTransformation

class WorkActivity : AppCompatActivity() {

    private lateinit var bannerImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var addToListButton: Button
    private lateinit var coverImageView: ImageView
    private lateinit var categoriesTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var excerptTextView: TextView
    private lateinit var excerptCommentTextView: TextView
    private lateinit var noteTextView: TextView
    private lateinit var seriesTextView: TextView

    //    private lateinit var coversSlider:
    private lateinit var editionNameTextView: TextView
    private lateinit var physicalTextView: TextView
    private lateinit var numOfPagesTextView: TextView
    private lateinit var publisherTextView: TextView
    private lateinit var contributorsTextView: TextView
    private lateinit var languagesTextView: TextView

    private lateinit var workViewModel: WorkViewModel

    private lateinit var workID: String
    private lateinit var bookID: String
    private lateinit var authorList: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)
        prepareViewModel()
        initComponents()

        workViewModel.workListLiveData.observe(this) { work ->
            Log.d("MAHMOUD", "testWork: $work")
            Log.d("MAHMOUD", "testWorkBook: ${work.book}")
            Log.d("MAHMOUD", "testWorkRating: ${work.ratings}")

            Glide.with(this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(
//                    if (bookID.isEmpty()) {
                        UtilMethods.createCoverUrl(
                            "${work.covers?.first() ?: -1}",
                            CoverKey.ID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
//                    } else {
//                        UtilMethods.createCoverUrl(
//                            bookID,
//                            CoverKey.OLID.name.lowercase(),
//                            CoverSize.MEDIUM.query,
//                        )
//                    },
                )
                .apply(RequestOptions.bitmapTransform(BlurTransformation(2, 3)))
                .into(bannerImageView)

            titleTextView.text = work.title ?: ""
            authorTextView.text = authorList
            "(${work.firstPublishDate ?: (work.book?.publishDate ?: "")})".also {
                yearTextView.text = it
            }
            "(${work.ratings?.summary?.count ?: 0})".also { ratingTextView.text = it }
            ratingBar.progress = (work.ratings?.summary?.average?.times(100))?.toInt() ?: 0

            Glide.with(this)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(
//                    if (bookID.isNullOrEmpty()) {
                        UtilMethods.createCoverUrl(
                            "${work.covers?.first() ?: -1}",
                            CoverKey.ID.name.lowercase(),
                            CoverSize.MEDIUM.query,
                        )
//                    } else {
//                        UtilMethods.createCoverUrl(
//                            bookID,
//                            CoverKey.OLID.name.lowercase(),
//                            CoverSize.MEDIUM.query,
//                        )
//                    },
                )
                .into(coverImageView)

            work.subjects.apply {
                if (this.isNullOrEmpty()) {
                    categoriesTextView.visibility = View.GONE
                } else {
                    categoriesTextView.text = Category.values().filter { category ->
                        this.map { subject -> subject.lowercase() }
                            .contains(category.query.replace("_", " "))
                    }.joinToString(", ") {
                        it.name.lowercase().replaceFirstChar { c -> c.uppercase() }
                    }
                }
            }

            work.description.apply {
                if (this == null) {
                    descriptionTextView.visibility = View.GONE
                } else {
                    descriptionTextView.text = this.value?.clearSources() ?: "No Description"
                }
            }
            work.excerpts.apply {
                if (isNullOrEmpty()) {
                    excerptTextView.visibility = View.GONE
                    excerptCommentTextView.visibility = View.GONE
                } else {
                    "\"${work.excerpts!![0].excerpt}\"".also { excerptTextView.text = it }
                    "- ${work.excerpts[0].comment}".also { excerptCommentTextView.text = it }
                }
            }
            work.book.apply {
                if (this != null) {
                    this.notes.apply {
                        if (this != null) {
                            if (this.value == null) {
                                noteTextView.visibility =
                                    View.GONE
                            } else {
                                "Notes: ${this.value}".also { noteTextView.text = it }
                            }
                        }
                    }
                    this.series.apply {
                        if (isNullOrEmpty()) {
                            seriesTextView.visibility =
                                View.GONE
                        } else {
                            "Series: ${this.joinToString(", ")}".also { seriesTextView.text = it }
                        }
                    }

                    this.editionName.apply {
                        if (isNullOrEmpty()) {
                            editionNameTextView.visibility =
                                View.GONE
                        } else {
                            "Edition: $this".also { editionNameTextView.text = it }
                        }
                    }
                    this.physicalFormat.apply {
                        if (isNullOrEmpty()) {
                            physicalTextView.visibility =
                                View.GONE
                        } else {
                            "Physical Format: $this".also { physicalTextView.text = it }
                        }
                    }
                    this.numberOfPages.apply {
                        if (this == null) {
                            numOfPagesTextView.visibility =
                                View.GONE
                        } else {
                            "$this pages".also { numOfPagesTextView.text = it }
                        }
                    }
                    this.publishers.apply {
                        if (isNullOrEmpty()) {
                            publisherTextView.visibility =
                                View.GONE
                        } else {
                            "Publishers: ${this.joinToString(", ")}".also {
                                publisherTextView.text = it
                            }
                        }
                    }

                    this.contributors.apply {
                        if (isNullOrEmpty()) {
                            contributorsTextView.visibility =
                                View.GONE
                        } else {
                            "Contributors: ${this.joinToString(", ") { "${it.name}: ${it.role} " }}"
                                .also { contributorsTextView.text = it }
                        }
                    }

                    this.languages.apply {
                        if (isNullOrEmpty()) {
                            languagesTextView.visibility =
                                View.GONE
                        } else {
                            "Languages: ${this.joinToString(", ") { it.key?.extractIdFromKey()!! }}"
                                .also { languagesTextView.text = it }
                        }
                    }
                } else {
                    noteTextView.visibility = View.GONE
                    seriesTextView.visibility = View.GONE
                    editionNameTextView.visibility = View.GONE
                    physicalTextView.visibility = View.GONE
                    numOfPagesTextView.visibility = View.GONE
                    publisherTextView.visibility = View.GONE
                    contributorsTextView.visibility = View.GONE
                    languagesTextView.visibility = View.GONE
                }
            }
        }

        workViewModel.fetchWork(workID, bookID)
    }

    private fun prepareViewModel() {
        val workRepo = WorkRepoImpl(OpenLibApiClient)
        val workViewModelFactory = WorkViewModelFactory(workRepo)
        workViewModel = ViewModelProvider(this, workViewModelFactory)[WorkViewModel::class.java]
    }

    private fun initComponents() {
        bannerImageView = findViewById(R.id.work_banner_iv)
        titleTextView = findViewById(R.id.work_title_tv)
        authorTextView = findViewById(R.id.work_author_tv)
        yearTextView = findViewById(R.id.work_year_tv)
        ratingTextView = findViewById(R.id.work_rating_tv)
        ratingBar = findViewById(R.id.work_rating_bar)
        addToListButton = findViewById(R.id.work_add_to_list_btn)
        coverImageView = findViewById(R.id.work_cover_iv)
        categoriesTextView = findViewById(R.id.work_categories_tv)
        descriptionTextView = findViewById(R.id.work_description_tv)
        excerptTextView = findViewById(R.id.work_excerpt_tv)
        excerptCommentTextView = findViewById(R.id.work_excerpt_comment_tv)
        noteTextView = findViewById(R.id.work_note_tv)
        seriesTextView = findViewById(R.id.work_series_tv)
        editionNameTextView = findViewById(R.id.work_edition_tv)
        physicalTextView = findViewById(R.id.work_physical_tv)
        numOfPagesTextView = findViewById(R.id.work_pages_tv)
        publisherTextView = findViewById(R.id.work_publishers_tv)
        contributorsTextView = findViewById(R.id.work_contributors_tv)
        languagesTextView = findViewById(R.id.work_languages_tv)

        intent.apply {
            workID = getStringExtra(Constants.WORK_KEY) ?: ""
            bookID = getStringExtra(Constants.BOOK_KEY) ?: ""
            authorList = getStringExtra(Constants.AUTHOR_LIST) ?: ""
        }
        Log.d("MAHMOUD", "testWorkIntent: $workID, $bookID, $authorList")
    }
}
