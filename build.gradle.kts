plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.0-alpha11").apply(false)
    id("com.android.library").version("8.1.0-alpha11").apply(false)
    kotlin("android").version("1.8.10").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
    id("dev.icerock.mobile.multiplatform-resources").version("0.21.1")
    id("com.google.gms.google-services").version("4.3.15").apply(false)
    id("com.google.firebase.crashlytics").version("2.9.6").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
