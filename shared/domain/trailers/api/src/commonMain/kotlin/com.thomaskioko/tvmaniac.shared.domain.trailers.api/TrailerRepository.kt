package com.thomaskioko.tvmaniac.shared.domain.trailers.api

import com.thomaskioko.tvmaniac.core.db.Trailers
import com.thomaskioko.tvmaniac.core.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface TrailerRepository {
    fun observeTrailersByShowId(showId: Long): Flow<Resource<List<Trailers>>>
}