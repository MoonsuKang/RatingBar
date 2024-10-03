package com.kms.ratingbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.floor
import kotlin.math.min

/**
 * CustomRatingBar is a custom view that displays a rating system using stars.
 * The number of stars, size, spacing, and color can be customized. It also supports user interaction to change the rating.
 *
 * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
 * @param attrs The attributes of the XML tag that is inflating the view.
 * @param defStyleAttr An attribute in the current theme that contains a reference to a style resource.
 */

class CustomRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var starCount: Int = 5 // Total number of stars
    private var currentRating: Float = 0f // Current rating
    private var stepSize: Float = 0.25f // Step size for rating (e.g., 0.25)
    private var isIndicator: Boolean = false // Determines if the rating is only for display or can be changed
    private var starSize: Float = 0f // Size of each star
    private var starSpacing: Float = 0f // Spacing between stars

    // Default star color
    private var starColor: Int = Color.RED

    // Color filter for stars
    private var colorFilter: PorterDuffColorFilter? = null

    // Scaled bitmaps for drawing stars
    private var scaledEmptyStar: Bitmap? = null
    private var scaledQuarterStar: Bitmap? = null
    private var scaledHalfStar: Bitmap? = null
    private var scaledThreeQuarterStar: Bitmap? = null
    private var scaledFullStar: Bitmap? = null

    // Bitmaps used for drawing stars
    private val emptyStar: Bitmap = getBitmapFromVectorDrawable(context, R.drawable.ic_star_empty)
    private val quarterStar: Bitmap = getBitmapFromVectorDrawable(context, R.drawable.ic_star_quarter)
    private val halfStar: Bitmap = getBitmapFromVectorDrawable(context, R.drawable.ic_star_half)
    private val threeQuarterStar: Bitmap = getBitmapFromVectorDrawable(context, R.drawable.ic_star_three_quarter)
    private val fullStar: Bitmap = getBitmapFromVectorDrawable(context, R.drawable.ic_star_full)

    private val paint = Paint()

    private var onRatingChangedListener: OnRatingChangedListener? = null

    init {
        // Apply XML attributes
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomRatingBar, 0, 0)
            starCount = typedArray.getInt(R.styleable.CustomRatingBar_ratingCount, starCount)
            currentRating = typedArray.getFloat(R.styleable.CustomRatingBar_rating, currentRating)
            stepSize = typedArray.getFloat(R.styleable.CustomRatingBar_stepSize, stepSize)
            isIndicator = typedArray.getBoolean(R.styleable.CustomRatingBar_isIndicator, isIndicator)
            starSize = typedArray.getDimension(R.styleable.CustomRatingBar_starSize, context.dpToPx(24))
            starSpacing = typedArray.getDimension(R.styleable.CustomRatingBar_starSpacing, context.dpToPx(4))

            // Get star color from XML, default is black
            starColor = typedArray.getColor(R.styleable.CustomRatingBar_starColor, Color.BLACK)
            typedArray.recycle()
        }

        // Set the star color and initialize the bitmaps
        setStarColor(starColor) // Initialize with star color from XML
        scaleBitmaps() // Initial scaling of bitmaps
    }


    /**
     * Measures the size of the CustomRatingBar based on the number of stars, their size, and spacing.
     *
     * @param widthMeasureSpec Horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec Vertical space requirements as imposed by the parent.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = (starCount * starSize + (starCount - 1) * starSpacing).toInt() // Include spacing between stars
        val height = starSize.toInt()
        setMeasuredDimension(width, height)
    }

    /**
     * Draws the stars on the canvas, with each star's appearance based on the current rating.
     *
     * @param canvas The Canvas on which the stars are drawn.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.colorFilter = colorFilter

        for (i in 0 until starCount) {
            val left = i * (starSize + starSpacing)
            val ratingDiff = currentRating - i

            // Determine which bitmap to draw based on the rating
            val starToDraw: Bitmap = when {
                ratingDiff >= 1 -> scaledFullStar ?: fullStar
                ratingDiff >= 0.75 -> scaledThreeQuarterStar ?: threeQuarterStar
                ratingDiff >= 0.5 -> scaledHalfStar ?: halfStar
                ratingDiff >= 0.25 -> scaledQuarterStar ?: quarterStar
                else -> scaledEmptyStar ?: emptyStar
            }

            canvas.drawBitmap(starToDraw, left, 0f, paint)
        }
    }

    /**
     * Handles touch events for changing the rating when the user interacts with the CustomRatingBar.
     *
     * @param event The MotionEvent object containing full information about the event.
     * @return True if the event was handled, false otherwise.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isIndicator) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val newRating = calculateRatingFromTouch(event.x)
                setRating(newRating)
                return true
            }
            MotionEvent.ACTION_UP -> {
                // Call performClick when the user lifts their finger
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * Handles the click action, ensuring accessibility services can trigger it.
     *
     * @return True if the click action was handled.
     */
    override fun performClick(): Boolean {
        super.performClick()
        return true
    }


    /**
     * Calculates the rating based on the user's touch position.
     *
     * @param x The X coordinate of the touch event.
     * @return The calculated rating based on the position.
     */
    private fun calculateRatingFromTouch(x: Float): Float {
        val fullStarWidth = starSize + starSpacing
        val rawRating = x / fullStarWidth
        val actualRating = floor(rawRating / stepSize) * stepSize
        return min(actualRating, starCount.toFloat())
    }

    /**
     * Sets the current rating and invalidates the view for re-drawing.
     *
     * @param rating The new rating value to set.
     */
    fun setRating(rating: Float) {
        currentRating = rating
        invalidate()
        onRatingChangedListener?.onRatingChanged(currentRating)
    }

    /**
     * Returns the current rating.
     *
     * @return The current rating value.
     */
    fun getRating(): Float = currentRating

    /**
     * Enables or disables interaction for changing the rating.
     *
     * @param indicator If true, the rating cannot be changed by user interaction.
     */
    fun setIsIndicator(indicator: Boolean) {
        this.isIndicator = indicator
    }

    /**
     * Sets the color of the stars and re-draws the view.
     *
     * @param color The color to set for the stars.
     */
    fun setStarColor(color: Int) {
        this.starColor = color
        colorFilter = PorterDuffColorFilter(starColor, PorterDuff.Mode.SRC_IN) // Update color filter
        invalidate() // Redraw the view
    }

    /**
     * Gets the current star color.
     *
     * @return The current star color.
     */
    fun getStarColor(): Int {
        return starColor
    }

    /**
     * Sets the size of the stars and requests a layout update.
     *
     * @param size The size of the stars in pixels.
     */
    fun setStarSize(size: Float) {
        this.starSize = size
        scaleBitmaps() // Scale the bitmaps based on the new size
        requestLayout()
    }

    /**
     * Sets the spacing between stars and requests a layout update.
     *
     * @param spacing The spacing between the stars in pixels.
     */
    fun setStarSpacing(spacing: Float) {
        this.starSpacing = spacing
        requestLayout()
    }

    /**
     * Sets the listener to be notified when the rating changes.
     *
     * @param listener The listener to notify.
     */
    fun setOnRatingChangedListener(listener: OnRatingChangedListener) {
        this.onRatingChangedListener = listener
    }

    /**
     * Scales the bitmaps used for drawing stars to match the current star size.
     */
    private fun scaleBitmaps() {
        scaledEmptyStar = Bitmap.createScaledBitmap(emptyStar, starSize.toInt(), starSize.toInt(), false)
        scaledQuarterStar = Bitmap.createScaledBitmap(quarterStar, starSize.toInt(), starSize.toInt(), false)
        scaledHalfStar = Bitmap.createScaledBitmap(halfStar, starSize.toInt(), starSize.toInt(), false)
        scaledThreeQuarterStar = Bitmap.createScaledBitmap(threeQuarterStar, starSize.toInt(), starSize.toInt(), false)
        scaledFullStar = Bitmap.createScaledBitmap(fullStar, starSize.toInt(), starSize.toInt(), false)
    }
}
