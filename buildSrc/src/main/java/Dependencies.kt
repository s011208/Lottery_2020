object Version {
    const val roomVersion = "2.2.5"
    const val koinVersion = "2.1.6"
    const val workVersion = "2.4.0"
    const val preferenceVersion = "1.1.1"
}

object Dependencies {
    const val kotlinVersion = "org.jetbrains.kotlin:kotlin-stdlib:1.3.72"
    const val coreKtx = "androidx.core:core-ktx:1.3.1"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val constrainLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val junit = "junit:junit:4.12"
    const val extJunit = "androidx.test.ext:junit:1.1.1"
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.19"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val flipper = "com.facebook.flipper:flipper:0.51.0"
    const val soLoader = "com.facebook.soloader:soloader:0.9.0"
    const val flipperNoOp = "com.facebook.flipper:flipper-noop:0.51.0"

    // https://developer.android.com/training/data-storage/room
    const val roomRuntime = "androidx.room:room-runtime:${Version.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.roomVersion}"
    const val roomRxJava = "androidx.room:room-rxjava2:${Version.roomVersion}"

    const val koinCore = "org.koin:koin-core:${Version.koinVersion}"
    const val koinAndroid = "org.koin:koin-android:${Version.koinVersion}"
    const val koinAndroidScope = "org.koin:koin-android-scope:${Version.koinVersion}"
    const val koinAndroidViewModel = "org.koin:koin-android-viewmodel:${Version.koinVersion}"
    const val koinAndroidExt = "org.koin:koin-android-ext:${Version.koinVersion}"
    const val koinAndroidxScope = "org.koin:koin-androidx-scope:${Version.koinVersion}"
    const val koinAndroidxViewModel = "org.koin:koin-androidx-viewmodel:${Version.koinVersion}"
    const val koinAndroidxExt = "org.koin:koin-androidx-ext:${Version.koinVersion}"
    const val koinAndroidKtor = "org.koin:koin-ktor:${Version.koinVersion}"

    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:17.4.4"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:17.1.1"

    const val jsoup = "org.jsoup:jsoup:1.12.1"
    const val workKtx = "androidx.work:work-runtime-ktx:${Version.workVersion}"
    const val workRxJava = "androidx.work:work-rxjava2:${Version.workVersion}"
    const val preferenceKotlin = "androidx.preference:preference-ktx:${Version.preferenceVersion}"
}