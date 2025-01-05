plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.protocole"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.protocole"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.itextpdf.android:kernel-android:7.2.5")
    implementation ("com.itextpdf.android:itext7-core-android:7.2.5")
    implementation ("com.itextpdf:itextpdf:5.5.13.4")
    implementation ("com.github.gcacace:signature-pad:1.3.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}