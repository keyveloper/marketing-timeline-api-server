package org.example.marketingtimelineapiserver.dto

data class GetTimelineAdsApiRequest(
    val influencerId: String,
    val cursor: Long?,
    val limit: Int = 20
)
