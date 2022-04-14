import util.libs

plugins {
    `kmm-domain-plugin`
}

android {
    namespace = "com.thomaskioko.tvmaniac.shared.domain.discover.implementation"
}

dependencies {
    commonMainImplementation(projects.shared.core.util)
    commonMainImplementation(projects.shared.remote)
    commonMainImplementation(projects.shared.database)
    commonMainImplementation(projects.shared.domain.discover.api)
    commonMainImplementation(projects.shared.domain.showCommon.api)

    commonMainImplementation(libs.kermit)
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.kotlin.datetime)
    commonMainImplementation(libs.multiplatform.paging.core)
    commonMainImplementation(libs.squareup.sqldelight.extensions)

    testImplementation(libs.testing.mockk.core)

    commonTestImplementation(kotlin("test"))
    commonTestImplementation(projects.shared.core.test)
    commonTestImplementation(libs.testing.turbine)
    commonTestImplementation(libs.testing.kotest.assertions)
    commonTestImplementation(libs.testing.coroutines.test)
    commonTestImplementation(libs.testing.mockk.common)

    val coroutineCore = libs.kotlin.coroutines.core.get()

    @Suppress("UnstableApiUsage")
    iosMainImplementation("${coroutineCore.module.group}:${coroutineCore.module.name}:${coroutineCore.versionConstraint.displayName}") {
        version {
            strictly(libs.versions.coroutines.native.get())
        }
    }
}