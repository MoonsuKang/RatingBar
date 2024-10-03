package com.kms.ratingbar

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kms.ratingbar.sample.R.layout.activity_main)

        val customRatingBar =
            findViewById<CustomRatingBar>(com.kms.ratingbar.sample.R.id.customRatingBar)
        val ratingTextView = findViewById<TextView>(com.kms.ratingbar.sample.R.id.ratingTextView)
        val buttonRandomRating =
            findViewById<Button>(com.kms.ratingbar.sample.R.id.buttonRandomRating)
        val buttonChangeColor =
            findViewById<Button>(com.kms.ratingbar.sample.R.id.buttonChangeColor)
        val buttonChangeSize = findViewById<Button>(com.kms.ratingbar.sample.R.id.buttonChangeSize)

        ratingTextView.text = customRatingBar.getRating().toString()
        customRatingBar.setOnRatingChangedListener(object : OnRatingChangedListener {
            override fun onRatingChanged(rating: Float) {
                ratingTextView.text = String.format("%.2f", rating) // Update the rating text
            }
        })


        // CASE1: random rating change
        buttonRandomRating.setOnClickListener {
            val randomRating = Random.nextFloat() * customRatingBar.starCount
            customRatingBar.setRating(randomRating)
            ratingTextView.text = String.format("%.2f", randomRating) // Update the rating text
        }

        // CASE2: random color change
        buttonChangeColor.setOnClickListener {
            val randomColor =
                Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            customRatingBar.setStarColor(randomColor)
        }

        // CASE3 random size change
        buttonChangeSize.setOnClickListener {
            val randomSize = Random.nextInt(20, 80).toFloat()
            customRatingBar.setStarSize(randomSize)
            customRatingBar.invalidate() // redraw
        }
    }

}