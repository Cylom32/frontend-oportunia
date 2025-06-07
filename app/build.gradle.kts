plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)

   // id("org.jetbrains.kotlin.kapt")


    id("com.google.gms.google-services") version "4.4.2" apply false

    kotlin("kapt")

    ///////////////////////////




}

android {
    namespace = "com.example.oportunia"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.oportunia"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {




    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.compose.material)


    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.accompanist.swiperefresh)





    // Retrofit + Gson + OkHttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)


    implementation(libs.hilt.android)
    implementation(libs.androidx.documentfile)
    kapt(libs.hilt.compiler)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)

    //implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    implementation(libs.converter.moshi)

    implementation("androidx.datastore:datastore-preferences:1.0.0")


    implementation(libs.androidx.material.icons.extended)

    implementation("io.coil-kt:coil-compose:2.4.0")


    ///////////////////////////////////

    /////-----------------------------         lo nuevo por aquello                ---------------------------------/////////////


    implementation ("org.mindrot:jbcrypt:0.4")



    implementation ("androidx.documentfile:documentfile:1.0.1")
//    implementation("com.squareup.moshi:moshi:1.15.0")
//    // Moshi para Kotlin (reflect), opcional si no usas code-gen en todos
//    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
//    // Code-gen de Moshi
//    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    ///////////////////////////////////

}

apply(plugin = "com.google.gms.google-services")

kapt {
    correctErrorTypes = true
}