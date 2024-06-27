plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)


}

android {
    namespace = "com.example.renthostelfinder"
    compileSdk = 34

    defaultConfig {
        multiDexEnabled = true
        applicationId = "com.example.renthostelfinder"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}


dependencies {

        implementation (libs.material3)
        implementation (libs.androidx.ui.v140 )// Ensure you have the Compose UI dependency
        implementation (libs.ui.tooling.preview)
        implementation (libs.androidx.material)
        implementation (libs.coil.compose.v210 )// For image loading
        implementation (libs.google.firebase.firestore.ktx)
        implementation (libs.firebase.storage.ktx.v2020)

    implementation (libs.androidx.material3.v101)
    implementation (libs.ui)
    implementation (libs.androidx.ui.tooling.preview.v140)
    implementation (libs.androidx.material.v140)
    implementation (libs.coil.compose)


    implementation (libs.kotlin.stdlib)
    implementation(libs.coil.compose)
    implementation (libs.androidx.core.ktx.v1101)
    implementation (libs.ui)
    implementation (libs.androidx.compiler.v140)
    implementation (libs.multidex)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (platform(libs.firebase.bom))
    implementation (libs.firebase.firestore.ktx)
    implementation (libs.firebase.auth.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
