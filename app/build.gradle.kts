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
        versionCode = 9
        versionName = "2.3"
        buildConfigField("String", "BASE_URL", "\"http://128.199.113.218:8000/api/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        resourceConfigurations += "km"
        resourceConfigurations += "en"
    }

    flavorDimensions.add("MajorRecommendation")

    productFlavors {
        create("dev") {
            dimension = "MajorRecommendation"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8000/api/\"")
        }
        create("uat") {
            dimension = "MajorRecommendation"
            buildConfigField("String", "BASE_URL", "\"http://128.199.113.218:8000/api/\"")
        }
        create("prd") {
            dimension = "MajorRecommendation"
            buildConfigField("String", "BASE_URL", "\"https://major.muutiitaa.com/api/\"")
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

    // Kotlin
    implementation("androidx.core:core-ktx:1.13.1")
    // Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    // AppCompat
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")
    implementation(composeBom)

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Material Design 3
    implementation("androidx.compose.material3:material3")

    // Integration with activities
    implementation("androidx.activity:activity-compose:1.9.3")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    // Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata:1.7.6")
    // Integration with Navigation
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Retrofit for network requests
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation("com.google.firebase:firebase-crashlytics:19.3.0")
    implementation("com.google.firebase:firebase-analytics:22.1.2")

    // Add the dependency for the Performance Monitoring library
    implementation("com.google.firebase:firebase-perf:21.0.3")

    // Add the dependency for the Cloud Messaging library
    implementation("com.google.firebase:firebase-messaging:24.1.0")
    implementation("com.google.firebase:firebase-inappmessaging-display:21.0.1")

    // Add the dependency for the Remote Config library
    implementation("androidx.security:security-crypto:1.0.0")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.6")
}