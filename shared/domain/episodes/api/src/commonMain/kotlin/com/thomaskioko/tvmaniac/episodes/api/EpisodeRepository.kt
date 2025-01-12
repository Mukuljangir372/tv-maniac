package com.thomaskioko.tvmaniac.episodes.api

import com.thomaskioko.tvmaniac.core.db.EpisodesBySeasonId
import com.thomaskioko.tvmaniac.core.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun observeSeasonEpisodes(
        tvShowId: Long,
        seasonId: Long,
        seasonNumber: Long
    ): Flow<Resource<List<EpisodesBySeasonId>>>
}
