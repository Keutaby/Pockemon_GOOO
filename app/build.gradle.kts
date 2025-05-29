plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //Plugins para utilizar el navigation compose en mi entorno
    alias(libs.plugins.jetbrainsKotlinSerialization)

}

android {
    namespace = "com.example.perfil_usuario"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.perfil_usuario"
        minSdk = 24
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
    //Para cargar el navigation compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Aqui va la libreria de OSMDroid
    //https://github.com/osmdroid/osmdroid
    implementation("org.osmdroid:osmdroid-android:6.1.14")

    //Libreria para usar el GPS del celular
    implementation(libs.play.services.location)

    //Para jalar el observeAsState
    implementation("androidx.compose.runtime:runtime-livedata:1.8.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.android)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)

    implementation(libs.androidx.media3.common.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Librerias para Retrofit
    val version_retrofit = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:${version_retrofit}") //Este se va a encargar de hacer las peticiones HTTP
    implementation("com.squareup.retrofit2:converter-gson:${version_retrofit}") //Este convierte de JSON a objetos de kotlin

    //Corutinas, libreria para usar programacion asincrona
    val version_corutinas = "1.5.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${version_corutinas}")

    //Libreria para checar la infomacion en tiempo real
    implementation("androidx.compose.runtime:runtime-livedata:1.7.8")

    implementation("io.coil-kt:coil-compose:2.6.0")


    //estas son para cargar camara x
    // CameraX core library using the camera2 implementation
    val camerax_version = "1.5.0-alpha06"
    // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    // If you want to additionally use the CameraX Lifecycle library
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    // If you want to additionally use the CameraX VideoCapture library
    implementation("androidx.camera:camera-video:${camerax_version}")
    // If you want to additionally use the CameraX View class
    implementation("androidx.camera:camera-view:${camerax_version}")
    // If you want to additionally add CameraX ML Kit Vision Integration
    implementation("androidx.camera:camera-mlkit-vision:${camerax_version}")
    // If you want to additionally use the CameraX Extensions library
    implementation("androidx.camera:camera-extensions:${camerax_version}")


}