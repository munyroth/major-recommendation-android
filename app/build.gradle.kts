plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Google services Gradle plugin
    id("com.google.gms.google-services")

    // Add the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")

    // Add the Performance Monitoring Gradle plugin
    id("com.google.firebase.firebase-perf")
}

android {
    namespace = "com.munyroth.majorrecommendation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.munyroth.majorrecommendation"
        minSdk = 30
        targetSdk = 34
        versionCode = 3
        versionName = "1.2"
        buildConfigField("String", "BASE_URL", "\"https://major.furniturestores.me/api/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("MajorRecommendation")

    productFlavors {
        create("uat") {
            dimension = "MajorRecommendation"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8000/api/\"")
        }
        create("prd") {
            dimension = "MajorRecommendation"
            buildConfigField("String", "BASE_URL", "\"https://major.furniturestores.me/api/\"")
        }
    }

    signingConfigs {
        create("uatRelease") {
            storeFile = file("firebase.keystore")
            storePassword = "734658"
            keyAlias = "firebase"
            keyPassword = "734658"
        }
        create("prdRelease") {
            storeFile = file("firebase.keystore")
            storePassword = "734658"
            keyAlias = "firebase"
            keyPassword = "734658"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            productFlavors.getByName("uat").signingConfig = signingConfigs.getByName("uatRelease")
            productFlavors.getByName("prd").signingConfig = signingConfigs.getByName("prdRelease")

        }

        debug {

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))

    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

    // Add the dependency for the Performance Monitoring library
    implementation("com.google.firebase:firebase-perf")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}