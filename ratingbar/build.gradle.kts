import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.vanniktech.maven)
    id("signing")
}

android {
    namespace = "com.kms.ratingbar"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.core.ktx)
}

// Maven Publishing 설정
mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates("io.github.moonsukang", "custom-rating-bar", "1.0.0")

    pom {
        name.set("CustomRatingBar")
        description.set("A highly customizable RatingBar library for Android with AndroidX support.")
        url.set("https://github.com/MoonsuKang/RatingBar")
        inceptionYear.set("2024")

        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("MoonsuKang")
                name.set("MoonsuKang")
                url.set("https://github.com/MoonsuKang")
            }
        }
        scm {
            url.set("https://github.com/MoonsuKang/RatingBar")
            connection.set("scm:git:git://github.com/MoonsuKang/RatingBar.git")
            developerConnection.set("scm:git:ssh://git@github.com:MoonsuKang/RatingBar.git")
        }
    }
}
