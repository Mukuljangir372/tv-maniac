package com.thomaskioko.tvmaniac.similar.implementation

import co.touchlab.kermit.Logger
import com.thomaskioko.tvmaniac.core.db.SelectSimilarShows
import com.thomaskioko.tvmaniac.core.util.ExceptionHandler.resolveError
import com.thomaskioko.tvmaniac.core.util.network.Resource
import com.thomaskioko.tvmaniac.core.util.network.networkBoundResource
import com.thomaskioko.tvmaniac.tmdb.api.model.TvShowsResponse
import com.thomaskioko.tvmaniac.showcommon.api.cache.TvShowCache
import com.thomaskioko.tvmaniac.similar.api.SimilarShowCache
import com.thomaskioko.tvmaniac.similar.api.SimilarShowsRepository
import com.thomaskioko.tvmaniac.tmdb.api.TmdbService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SimilarShowsRepositoryImpl(
    private val apiService: TmdbService,
    private val similarShowCache: SimilarShowCache,
    private val tvShowCache: TvShowCache,
    private val dispatcher: CoroutineDispatcher,
) : SimilarShowsRepository {

    override fun observeSimilarShows(showId: Long): Flow<Resource<List<SelectSimilarShows>>> =
        networkBoundResource(
            query = { similarShowCache.observeSimilarShows(showId) },
            shouldFetch = { it.isNullOrEmpty() },
            fetch = { apiService.getSimilarShows(showId) },
            saveFetchResult = { mapAndInsert(showId, it) },
            onFetchFailed = { Logger.withTag("observeSimilarShows").e { it.resolveError() } },
            coroutineDispatcher = dispatcher
        )

    private fun mapAndInsert(showId: Long, response: TvShowsResponse) {
        response.results.forEach { show ->
            tvShowCache.insert(show.toShow())

            similarShowCache.insert(
                showId = showId,
                similarShowId = show.id.toLong()
            )
        }
    }
}
