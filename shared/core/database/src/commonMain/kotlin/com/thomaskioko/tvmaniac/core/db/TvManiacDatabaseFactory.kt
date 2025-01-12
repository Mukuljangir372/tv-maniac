package com.thomaskioko.tvmaniac.core.db

import com.squareup.sqldelight.db.SqlDriver
import com.thomaskioko.tvmaniac.core.db.TvManiacDatabase
import com.thomaskioko.tvmaniac.core.db.Show

class TvManiacDatabaseFactory(
    private val driverFactory: DriverFactory
) {

    fun createDatabase(): TvManiacDatabase {
        return TvManiacDatabase(
            driver = driverFactory.createDriver(),
            showAdapter = Show.Adapter(
                genre_idsAdapter = intAdapter,
            )
        )
    }
}

expect class DriverFactory {
    fun createDriver(): SqlDriver
}
