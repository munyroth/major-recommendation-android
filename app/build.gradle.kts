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
        versionCode = 4
        versionName = "1.3"
        buildConfigField("String", "BASE_URL", "\"https://major.furniturestores.me/api/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("MajorRecommendation")

    productFlavors {
        create("dev") {
            dimension = "MajorRecommendation"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8000/api/\"")
        }
        create("uat") {
            dimension = "MajorRecommendation"
            buildConfigField("String", "BASE_URL", "\"http://192.168.3.59:8000/api/\"")
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.02.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Material Design 3
    implementation("androidx.compose.material3:material3")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Integration with activities
    implementation("androidx.activity:activity-compose:1.8.2")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    // Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // Integration with Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

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
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))

    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

    // Add the dependency for the Performance Monitoring library
    implementation("com.google.firebase:firebase-perf")

    // Add the dependency for the Cloud Messaging library
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-inappmessaging-display")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}