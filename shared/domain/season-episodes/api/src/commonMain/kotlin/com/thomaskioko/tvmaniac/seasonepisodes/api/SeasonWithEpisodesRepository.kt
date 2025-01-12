package com.thomaskioko.tvmaniac.seasonepisodes.api

import com.thomaskioko.tvmaniac.core.db.SelectSeasonWithEpisodes
import com.thomaskioko.tvmaniac.core.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface SeasonWithEpisodesRepository {
    fun observeSeasonWithEpisodes(
        showId: Long
    ): Flow<Resource<List<SelectSeasonWithEpisodes>>>
}
