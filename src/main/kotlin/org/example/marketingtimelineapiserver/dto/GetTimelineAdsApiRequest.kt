package org.example.marketingtimelineapiserver.dto

import org.example.marketingtimelineapiserver.enums.TimelineCursor
import java.util.UUID

data class GetTimelineAdsApiRequest(
    val influencerId: UUID,
    val cursor: TimelineCursor,
    val pivotTime: Long?
)
