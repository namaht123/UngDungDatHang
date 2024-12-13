plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.ung_dung_dat_hang"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ung_dung_dat_hang"
        minSdk = 24
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    ndkVersion = "25.1.8937393"
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Các thư viện androidx
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("net.sourceforge.jtds:jtds:1.3.1") // Thêm dòng này
    // Thay thế driver JDBC để kết nối với SQL Server
    implementation("com.microsoft.sqlserver:mssql-jdbc:11.2.0.jre8") // Phiên bản mới hơn có thể cần thiết

    // Thay thế driver JDBC để kết nối với SQL Server
    implementation("com.microsoft.sqlserver:mssql-jdbc:11.2.0.jre8") // Phiên bản mới hơn có thể cần thiết

    // Thư viện cho MySQL
    implementation("mysql:mysql-connector-java:8.0.32")

    implementation("com.github.bumptech.glide:glide:4.14.2")
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4) // Hoặc phiên bản mới nhất
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)



}
