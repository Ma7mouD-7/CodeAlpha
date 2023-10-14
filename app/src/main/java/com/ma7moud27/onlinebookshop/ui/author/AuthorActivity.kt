package com.ma7moud27.onlinebookshop.ui.author

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.color.utilities.TonalPalette
import com.ma7moud27.onlinebookshop.R
import com.ma7moud27.onlinebookshop.model.author.Author
import com.ma7moud27.onlinebookshop.network.openlibrary.OpenLibApiClient
import com.ma7moud27.onlinebookshop.repository.author.AuthorRepoImpl
import com.ma7moud27.onlinebookshop.utils.Constants.Companion.AUTHOR_KEY
import com.ma7moud27.onlinebookshop.utils.UtilMethods
import com.ma7moud27.onlinebookshop.utils.UtilMethods.Companion.clearSources
import com.ma7moud27.onlinebookshop.utils.enums.CoverKey
import com.ma7moud27.onlinebookshop.utils.enums.CoverSize
import com.ma7moud27.onlinebookshop.viewmodel.AuthorViewModel
import com.ma7moud27.onlinebookshop.viewmodel.factory.AuthorViewModelFactory
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlin.math.abs
import kotlin.math.roundToInt

class AuthorActivity : AppCompatActivity() {
    private lateinit var collapsingAvatarContainer: FrameLayout
    private lateinit var background: FrameLayout
    private lateinit var toolbar: Toolbar
    private lateinit var appBarLayout: AppBarLayout

    private lateinit var authorNameTextView: AppCompatTextView
    private lateinit var authorFullNameTextView: AppCompatTextView
    private lateinit var authorPicImageView: ImageView
    private lateinit var authorBannerImageView: ImageView
    private lateinit var authorPersonalNamePlaceholder: TextView
    private lateinit var authorPersonalNameTextView: TextView
    private lateinit var authorBirthDatePlaceholder: TextView
    private lateinit var authorBirthDateTextView: TextView
    private lateinit var authorDeathDatePlaceholder: TextView
    private lateinit var authorDeathDateTextView: TextView
    private lateinit var authorBioPlaceholder: TextView
    private lateinit var authorBioTextView: TextView
    private lateinit var authorAlternativeNamesPlaceholder: TextView
    private lateinit var authorAlternativeNamesArrowImageView: ImageView
    private lateinit var authorAlternativeNamesTextView: TextView

    private var margin: Float = 0F
    private var cashCollapseState: Pair<Int, Int>? = null
    private val mLowerLimitTransparently = ABROAD * 0.45
    private val mUpperLimitTransparently = ABROAD * 0.65
    private var startAvatarAnimatePointY: Float = 0F
    private var animateWeight: Float = 0F
    private var isCalculated = false
    private var isAlterOpened = false
    private var authorID = ""

    private lateinit var authorViewModel: AuthorViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)
        prepareViewModel()
        initComponents()

        Log.d("Mahmoud", "onCreate: $authorID")

        appBarLayout.addOnOffsetChangedListener { appBarLayout, i ->
            if (isCalculated.not()) {
                startAvatarAnimatePointY =
                    abs((appBarLayout.height - EXPAND_AVATAR_SIZE - toolbar.height / 2) / appBarLayout.totalScrollRange)
                animateWeight = 1 / (1 - startAvatarAnimatePointY)
                isCalculated = true
            }

            val offset = abs(i / appBarLayout.totalScrollRange.toFloat())
            updateViews(offset)
        }

        val alternativeClickListener: (View) -> Unit = {
            if (!isAlterOpened) {
                authorAlternativeNamesArrowImageView.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                authorAlternativeNamesTextView.visibility = View.VISIBLE
            } else {
                authorAlternativeNamesArrowImageView.setImageResource(R.drawable.ic_baseline_arrow_right_24)
                authorAlternativeNamesTextView.visibility = View.GONE
            }
            isAlterOpened = !isAlterOpened
        }
        authorAlternativeNamesPlaceholder.setOnClickListener(alternativeClickListener)
        authorAlternativeNamesTextView.setOnClickListener(alternativeClickListener)

        authorViewModel.authorLiveData.observe(this) { updateAuthorViews(it) }
        authorViewModel.fetchAuthor(authorID)
    }

    private fun prepareViewModel() {
        val authorRepo = AuthorRepoImpl(OpenLibApiClient)
        val authorViewModelFactory = AuthorViewModelFactory(authorRepo)
        authorViewModel = ViewModelProvider(this, authorViewModelFactory)[AuthorViewModel::class.java]
    }

    private fun initComponents() {
        EXPAND_AVATAR_SIZE = resources.getDimension(R.dimen.default_expanded_image_size)
        COLLAPSE_IMAGE_SIZE = resources.getDimension(R.dimen.default_collapsed_image_size)
        margin = resources.getDimension(R.dimen.avatar_margin)

        collapsingAvatarContainer = findViewById(R.id.stuff_container)
        appBarLayout = findViewById(R.id.app_bar_layout)
        toolbar = findViewById(R.id.anim_toolbar)
        toolbar.visibility = View.INVISIBLE
        background = findViewById(R.id.fl_background)

        authorNameTextView = findViewById(R.id.author_name_tv)
        authorFullNameTextView = findViewById(R.id.author_full_name_tv)
        authorPicImageView = findViewById(R.id.author_pic_iv)
        authorBannerImageView = findViewById(R.id.author_banner_iv)
        authorPersonalNamePlaceholder = findViewById(R.id.author_placeholder_personal_name_tv)
        authorPersonalNameTextView = findViewById(R.id.author_personal_name_tv)
        authorBirthDatePlaceholder = findViewById(R.id.author_placeholder_birth_date_tv)
        authorBirthDateTextView = findViewById(R.id.author_birth_date_tv)
        authorDeathDatePlaceholder = findViewById(R.id.author_placeholder_death_date_tv)
        authorDeathDateTextView = findViewById(R.id.author_death_date_tv)
        authorBioPlaceholder = findViewById(R.id.author_placeholder_bio_tv)
        authorBioTextView = findViewById(R.id.author_bio_tv)
        authorAlternativeNamesPlaceholder = findViewById(R.id.author_placeholder_alternative_Names_tv)
        authorAlternativeNamesArrowImageView = findViewById(R.id.author_alter_names_arrows_iv)
        authorAlternativeNamesTextView = findViewById(R.id.author_alternative_Names_tv)

        authorID = intent.getStringExtra(AUTHOR_KEY) ?: ""
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateAuthorViews(author: Author) {
        authorNameTextView.text = author.name
        authorFullNameTextView.text = author.fullName.ifEmpty { author.name }
        Glide.with(this)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(
                UtilMethods.createAuthorPicUrl(
                    author.photos.first().toString(),
                    CoverKey.ID.name.lowercase(),
                    CoverSize.MEDIUM.query,
                ),
            )
            .into(authorPicImageView)

        Glide.with(this)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(
                UtilMethods.createAuthorPicUrl(
                    author.photos.first().toString(),
                    CoverKey.ID.name.lowercase(),
                    CoverSize.MEDIUM.query,
                ),
            )
            .apply(RequestOptions.bitmapTransform(BlurTransformation(2, 3)))
            .into(authorBannerImageView)
           /* .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    this@AuthorActivity.startPostponedEnterTransition()
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    this@AuthorActivity.startPostponedEnterTransition()
                    if(resource !=null){
                         TonalPalette.
                    }
                }

            })*/

        authorPersonalNameTextView.apply {
            if (author.personalName.isNotEmpty()) {
                text = author.personalName
            } else { visibility = View.GONE; authorPersonalNamePlaceholder.visibility = View.GONE }
        }
        authorBirthDateTextView.apply {
            if (author.birthDate.isEmpty()) { visibility = View.GONE; authorBirthDatePlaceholder.visibility = View.GONE }
        }

        if (author.deathDate.isNotEmpty()) {
            authorBirthDateTextView.text = author.birthDate
            authorDeathDateTextView.text = "${author.deathDate}${UtilMethods.calculateAge(author.birthDate,author.deathDate) ?: ""}"
        } else {
            authorBirthDateTextView.text = "${author.birthDate}${UtilMethods.calculateAge(author.birthDate) ?: ""}"
            authorDeathDateTextView.visibility = View.GONE
            authorDeathDatePlaceholder.visibility = View.GONE
        }

        authorBioTextView.apply {
            if (author.bio.value.isNotEmpty()) {
                text = author.bio.value.clearSources()
            } else { visibility = View.GONE; authorBioPlaceholder.visibility = View.GONE }
        }
        authorAlternativeNamesTextView.apply {
            if (author.alternateNames.isNotEmpty()) {
                text = author.alternateNames.joinToString("\n")
            } else {
                visibility = View.GONE
                authorAlternativeNamesPlaceholder.visibility = View.GONE
                authorAlternativeNamesArrowImageView.visibility = View.GONE
            }
        }
    }

    private fun updateViews(percentOffset: Float) {
        /* Collapsing avatar transparent*/
        authorFullNameTextView.alpha = when {
            percentOffset > mUpperLimitTransparently -> 0.0F
            percentOffset < mUpperLimitTransparently -> 1f
            else -> 0.5F
        }

        /*Collapsed/expended sizes for views*/
        val result: Pair<Int, Int> = when {
            percentOffset < ABROAD -> Pair(TO_EXPANDED_STATE, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
            else -> Pair(TO_COLLAPSED_STATE, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
        }

        result.apply {
            var translationY = 0f
            var headContainerHeight = 0f
            val translationX: Float
            var currentImageSize = 0
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED_STATE -> {
                            translationY = toolbar.height.toFloat()
                            headContainerHeight = appBarLayout.totalScrollRange.toFloat()
                            currentImageSize = EXPAND_AVATAR_SIZE.toInt()

                            authorFullNameTextView.visibility = View.VISIBLE
                            authorNameTextView.visibility = View.INVISIBLE
                            background.setBackgroundColor(ContextCompat.getColor(this@AuthorActivity, R.color.color_transparent))

                            authorPicImageView.translationX = 0f
                        }

                        TO_COLLAPSED_STATE -> {
                            background.setBackgroundColor(ContextCompat.getColor(this@AuthorActivity, R.color.primary_color))
                            currentImageSize = COLLAPSE_IMAGE_SIZE.toInt()
                            translationY = appBarLayout.totalScrollRange.toFloat() - (toolbar.height - COLLAPSE_IMAGE_SIZE) / 2
                            headContainerHeight = toolbar.height.toFloat()
                            translationX = appBarLayout.width / 2f - COLLAPSE_IMAGE_SIZE / 2 - margin * 2

                            ValueAnimator.ofFloat(authorPicImageView.translationX, translationX).apply {
                                addUpdateListener {
                                    if (cashCollapseState!!.first == TO_COLLAPSED_STATE) {
                                        authorPicImageView.translationX = it.animatedValue as Float
                                    }
                                }
                                interpolator = AnticipateOvershootInterpolator()
                                startDelay = 69
                                duration = 350
                                start()
                            }

                            authorFullNameTextView.visibility = View.INVISIBLE
                            authorNameTextView.apply {
                                visibility = View.VISIBLE
                                alpha = 0.2f
                                this.translationX = width.toFloat() / 2
                                animate().translationX(0f)
                                    .setInterpolator(AnticipateOvershootInterpolator())
                                    .alpha(1.0f)
                                    .setStartDelay(69)
                                    .setDuration(450)
                                    .setListener(null)
                            }
                        }
                    }

                    authorPicImageView.apply {
                        layoutParams.height = currentImageSize
                        layoutParams.width = currentImageSize
                    }
                    collapsingAvatarContainer.apply {
                        layoutParams.height = headContainerHeight.toInt()
                        this.translationY = translationY
                        requestLayout()
                    }

                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    /* Collapse avatar img*/
                    authorPicImageView.apply {
                        if (percentOffset > startAvatarAnimatePointY) {
                            val animateOffset = (percentOffset - startAvatarAnimatePointY) * animateWeight
                            val avatarSize = EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * animateOffset

                            this.layoutParams.also {
                                if (it.height != avatarSize.roundToInt()) {
                                    it.height = avatarSize.roundToInt()
                                    it.width = avatarSize.roundToInt()
                                    this.requestLayout()
                                }
                            }

                            this.translationY = (COLLAPSE_IMAGE_SIZE - avatarSize) * animateOffset
                        } else {
                            this.layoutParams.also {
                                if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                                    it.height = EXPAND_AVATAR_SIZE.toInt()
                                    it.width = EXPAND_AVATAR_SIZE.toInt()
                                    this.layoutParams = it
                                }
                            }
                        }
                    }
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }
        }
    }

    companion object {
        const val ABROAD = 0.99f
        const val TO_EXPANDED_STATE = 0
        const val TO_COLLAPSED_STATE = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
        private var EXPAND_AVATAR_SIZE: Float = 0F
        private var COLLAPSE_IMAGE_SIZE: Float = 0F
    }

    // Function to apply blur effect and set it as background
//    fun loadImageWithBlur(context: Context, imageUrl: String, imageView: ImageView, radius: Float) {
//        val requestOptions = RequestOptions().transform(BlurTransformation(radius))
//        Glide.with(this).load(R.drawable.demo)
//            .bitmapTransform(BlurTransformation(context))
//            .into((ImageView) findViewById(R.id.image));
//        Glide.with(context)
//            .asBitmap()
//            .load(imageUrl)
//            .apply(requestOptions)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    val blurredBitmap = blurBitmap(context, resource, radius)
//                    val drawable = BitmapDrawable(context.resources, blurredBitmap)
//                    imageView.background = drawable
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // Not required for this example
//                }
//            })
//    }
}
