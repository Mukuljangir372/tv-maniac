package com.thomaskioko.tvmaniac.datasource.repository.tvshow

import com.thomaskioko.tvmaniac.datasource.cache.model.TvShows
import com.thomaskioko.tvmaniac.datasource.cache.shows.TvShowCache
import com.thomaskioko.tvmaniac.datasource.enums.TimeWindow
import com.thomaskioko.tvmaniac.datasource.enums.TimeWindow.WEEK
import com.thomaskioko.tvmaniac.datasource.enums.TvShowCategory
import com.thomaskioko.tvmaniac.datasource.enums.TvShowCategory.FEATURED
import com.thomaskioko.tvmaniac.datasource.enums.TvShowCategory.POPULAR_TV_SHOWS
import com.thomaskioko.tvmaniac.datasource.enums.TvShowCategory.TOP_RATED_TV_SHOWS
import com.thomaskioko.tvmaniac.datasource.enums.TvShowCategory.TRENDING
import com.thomaskioko.tvmaniac.datasource.mapper.toTvShowEntity
import com.thomaskioko.tvmaniac.datasource.network.api.TvShowsService

class TvShowsRepositoryImpl(
    private val apiService: TvShowsService,
    private val cache: TvShowCache
) : TvShowsRepository {

    override suspend fun getTvShow(tvShowId: Int): TvShows {
        return cache.getTvShow(tvShowId)
    }

    override suspend fun getPopularTvShows(page: Int): List<TvShows> {
        return if (cache.getTvShows().isEmpty()) {

            val entityList = apiService.getPopularShows(page).results
                .map { it.toTvShowEntity() }
                .map { it.copy(
                    showCategory = POPULAR_TV_SHOWS
                ) }

            cache.insert(entityList)

            getShowsByCategory(POPULAR_TV_SHOWS)
        } else {
            getShowsByCategory(POPULAR_TV_SHOWS)
        }
    }

    override suspend fun getTopRatedTvShows(page: Int): List<TvShows> {
        return if (cache.getTvShows().isEmpty()) {

            apiService.getTopRatedShows(page).results
                .map { it.toTvShowEntity() }
                .map { it.copy(
                    showCategory = POPULAR_TV_SHOWS
                ) }
                .map { cache.insert(it) }

            getShowsByCategory(TOP_RATED_TV_SHOWS)
        } else {
            getShowsByCategory(TOP_RATED_TV_SHOWS)
        }
    }

    override suspend fun getTrendingShows(
        timeWindow: String
    ): List<TvShows> {
        return if (getShowsByCategoryAndWindow(TRENDING, TimeWindow[timeWindow]).isEmpty()) {

            apiService.getTrendingShows(timeWindow).results
                .map { it.toTvShowEntity() }
                .map {
                    it.copy(
                        showCategory = TRENDING,
                        timeWindow = TimeWindow[timeWindow]
                    )
                }
                .map { cache.insert(it) }

            getShowsByCategoryAndWindow(TRENDING, TimeWindow[timeWindow])
        } else {
            getShowsByCategoryAndWindow(TRENDING, TimeWindow[timeWindow])
        }
    }

    override suspend fun getFeaturedShows(): List<TvShows> {
        return if (getShowsByCategoryAndWindow(FEATURED, WEEK).isEmpty()) {

            apiService.getTrendingShows(WEEK.window).results
                .map { it.toTvShowEntity() }
                .map {
                    it.copy(
                        showCategory = FEATURED,
                        timeWindow = WEEK
                    )
                }
                .map { cache.insert(it) }

            cache.getFeaturedTvShows(FEATURED, WEEK)
        } else {
            cache.getFeaturedTvShows(FEATURED, WEEK)
        }
    }

    override suspend fun getShowsByCategoryAndWindow(
        category: TvShowCategory,
        timeWindow: TimeWindow
    ): List<TvShows> {
        return cache.getTvShows(category, timeWindow)
    }

    private fun getShowsByCategory(category: TvShowCategory): List<TvShows> =
        cache.getTvShows()
            .filter { it.showCategory == category }

}
