package com.thomaskioko.tvmaniac.similar.api

import com.thomaskioko.tvmaniac.core.db.SelectSimilarShows
import kotlinx.coroutines.flow.Flow

interface SimilarShowCache {

    fun insert(showId: Long, similarShowId: Long)

    fun observeSimilarShows(showId: Long): Flow<List<SelectSimilarShows>>
}
