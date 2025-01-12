package com.thomaskioko.tvmaniac.similar.implementation.di

import com.thomaskioko.tvmaniac.core.db.TvManiacDatabase
import com.thomaskioko.tvmaniac.shared.core.ui.di.DefaultDispatcher
import com.thomaskioko.tvmaniac.showcommon.api.cache.TvShowCache
import com.thomaskioko.tvmaniac.similar.api.SimilarShowCache
import com.thomaskioko.tvmaniac.similar.api.SimilarShowsRepository
import com.thomaskioko.tvmaniac.similar.implementation.SimilarShowCacheImpl
import com.thomaskioko.tvmaniac.similar.implementation.SimilarShowsRepositoryImpl
import com.thomaskioko.tvmaniac.tmdb.api.TmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SimilarModule {

    @Singleton
    @Provides
    fun provideSimilarShowCache(database: TvManiacDatabase): SimilarShowCache {
        return SimilarShowCacheImpl(database)
    }

    @Singleton
    @Provides
    fun provideRelatedShowsRepository(
        tmdbService: TmdbService,
        similarShowCache: SimilarShowCache,
        tvShowCache: TvShowCache,
        @DefaultDispatcher ioDispatcher: CoroutineDispatcher
    ): SimilarShowsRepository =
        SimilarShowsRepositoryImpl(
            apiService = tmdbService,
            similarShowCache = similarShowCache,
            tvShowCache = tvShowCache,
            dispatcher = ioDispatcher
        )

}
