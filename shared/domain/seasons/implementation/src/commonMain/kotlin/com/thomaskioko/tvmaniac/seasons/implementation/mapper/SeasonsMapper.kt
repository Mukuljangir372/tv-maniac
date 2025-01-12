package com.thomaskioko.tvmaniac.seasons.implementation.mapper

import com.thomaskioko.tvmaniac.core.db.Season
import com.thomaskioko.tvmaniac.tmdb.api.model.ShowDetailResponse

fun ShowDetailResponse.toSeasonCacheList(): List<Season> {
    return seasons.map { seasonResponse ->
        Season(
            id = seasonResponse.id.toLong(),
            tv_show_id = id.toLong(),
            season_number = seasonResponse.seasonNumber.toLong(),
            name = seasonResponse.name,
            overview = seasonResponse.overview,
            epiosode_count = seasonResponse.episodeCount.toLong()
        )
    }
}
