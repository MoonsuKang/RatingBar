# Simple RatingBar Library

<div align="start">
  <p align="start">
    <img src="https://img.shields.io/badge/AndroidX-Supported-brightgreen?style=for-the-badge&logo=Android"/>
    <img src="https://img.shields.io/badge/XML-Supported-blue?style=for-the-badge&logo=xml"/>
    <img src="https://img.shields.io/badge/API-28%2B-orange?style=for-the-badge&logo=Android"/>
  </p>
</div>


A highly customizable RatingBar library for Android with AndroidX support.  
This library provides step-based star ratings with increments of 0, 25, 50, 75, and 100.  
It allows for easy customization and integration into Android projects using AndroidX libraries.  

## Features

- Supports AndroidX libraries.
- Customizable star rating steps (0, 25, 50, 75, 100).
- Easy to change star size and color.
- Allows both touch interaction and non-interactive indicators.
- Random rating and color change for flexible use cases.

## Minimum SDK Version
The library supports Android SDK version 28 and above.

## Installation

Add the following to your `build.gradle` file:

```groovy
// Project level build.gradle
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// App level build.gradle
dependencies {
    implementation 'com.kms.ratingbar:custom-rating-bar:1.0.0'
}
```

## Usage

### XML Integration

You can integrate `CustomRatingBar` into your XML layouts like this:

```xml
<com.kms.ratingbar.CustomRatingBar
    android:id="@+id/customRatingBar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:starSize="32dp"
    app:starColor="@color/starColor"
    app:stepSize="0.25" />
```

### Programmatic Usage
If you want to configure the `CustomRatingBar` programmatically:

```kotlin
val customRatingBar = findViewById<CustomRatingBar>(R.id.customRatingBar)

// Set rating
customRatingBar.setRating(3.5f)

// Change star color
customRatingBar.setStarColor(Color.RED)

// Disable interaction
customRatingBar.setIsIndicator(true)
```

### Example
Here is a simple example of how you can integrate the `CustomRatingBar` into your app:

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ratingBar = findViewById<CustomRatingBar>(R.id.customRatingBar)
        ratingBar.setRating(2.5f)

        findViewById<Button>(R.id.buttonChangeColor).setOnClickListener {
            ratingBar.setStarColor(Color.BLUE)
        }

        findViewById<Button>(R.id.buttonChangeSize).setOnClickListener {
            ratingBar.setStarSize(48f)
        }
    }
}
```

### Customization Options

| Attribute   | Description                        | Example Values            |
|-------------|------------------------------------|---------------------------|
| `starSize`  | Defines the size of each star.     | `16dp`, `32sp`            |
| `starColor` | Changes the color of the stars.    | `#FF0000`, `@color/star`  |
| `stepSize`  | Adjusts the rating step size.      | `0.25`, `0.5`, `1.0`      |
| `isIndicator`| Disables touch interactions.      | `true`, `false`           |


### Preview
<br/>

Change Rating              |  Change Color
:-------------------------:|:-------------------------:
![](https://github.com/user-attachments/assets/c0d3ec8c-21fc-49c8-abee-dd01a5adc37b)  |   ![](https://github.com/user-attachments/assets/1dc89da5-dbd3-4e69-a680-37dc1be29785)
Change Size                |  
![](https://github.com/user-attachments/assets/2ca4fda5-f5ce-4baf-8079-d70e79d0876c)  |   ![](https://github.com/user-attachments/assets/placeholder-for-second-image)

<br/>

## Changelog

### Version 1.0.0
- Initial release.
- Customizable star size, color, and rating steps.
- Support for interactive and non-interactive modes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.



