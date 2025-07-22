package org.example.marketingtimelineapiserver.dto

data class GetTimelineAdsResult(
    val timelineAds: List<TimelineAdMetadata>,
    val nextCursor: Long?,
    val hasMore: Boolean
) {
    companion object {
        fun of(
            timelineAds: List<TimelineAdMetadata>,
            nextCursor: Long?,
            hasMore: Boolean
        ): GetTimelineAdsResult {
            return GetTimelineAdsResult(
                timelineAds = timelineAds,
                nextCursor = nextCursor,
                hasMore = hasMore
            )
        }
    }
}
