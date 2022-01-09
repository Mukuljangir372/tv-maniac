plugins {
    `android-app-plugin`
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":shared:core"))
    implementation(project(":shared:database"))
    implementation(project(":shared:remote"))
    implementation(project(":shared:domain:discover:api"))
    implementation(project(":shared:domain:discover:implementation"))
    implementation(project(":shared:domain:seasons:api"))
    implementation(project(":shared:domain:seasons:implementation"))
    implementation(project(":app-common:annotations"))
    implementation(project(":app-common:compose"))
    implementation(project(":app-common:navigation"))
    implementation(project(":app-features:discover"))
    implementation(project(":app-features:home"))
    implementation(project(":app-features:search"))
    implementation(project(":app-features:show-details"))
    implementation(project(":app-features:shows-grid"))
    implementation(project(":app-features:watchlist"))
    implementation(project(":app-features:settings"))
}
