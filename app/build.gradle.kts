plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2"
}
android {
    namespace = "edu.ou.flowerstore"
    compileSdk = 34
    android.buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "edu.ou.flowerstore"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "CLOUDINARY_API_KEY",
            "\"${providers.gradleProperty("CLOUDINARY_API_KEY").get()}\""
        )
        buildConfigField(
            "String",
            "CLOUDINARY_KEY_SECRET",
            "\"${providers.gradleProperty("CLOUDINARY_KEY_SECRET").get()}\""
        )
        buildConfigField(
            "String",
            "CLOUDINARY_NAME",
            "\"${providers.gradleProperty("CLOUDINARY_NAME").get()}\""
        )

        buildConfigField(
            "String",
            "ZALO_PAY_APP_ID",
            "\"${
                if (providers.gradleProperty("ZALO_PAY_APP_ID")
                        .isPresent()
                ) providers.gradleProperty("ZALO_PAY_APP_ID").get() else "0"
            }\""
        )
        buildConfigField(
            "String",
            "ZALO_PAY_KEY1",
            "\"${providers.gradleProperty("ZALO_PAY_KEY1").get()}\""
        )
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(files("../zpdk-release-v3.1.aar"))
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.testing)
    implementation(libs.room.guava)
    implementation(libs.picasso)
    implementation(libs.mpandroidchart)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.volley)
    implementation(libs.cloudinary.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.ui.auth)
    implementation(libs.facebook.android.sdk)
    implementation(libs.play.services.auth)
    implementation(libs.recyclerview)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.guava)
    implementation(libs.concurrent.futures)
    implementation(libs.annotation)
    implementation(libs.flexbox)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.fragment)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)

}
