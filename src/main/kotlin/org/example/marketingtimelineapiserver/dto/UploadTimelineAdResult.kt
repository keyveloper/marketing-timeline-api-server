package org.example.marketingtimelineapiserver.dto

data class UploadTimelineAdResult(
    val timelineAd: TimelineAdMetadata
) {
    companion object {
        fun of(timelineAd: TimelineAdMetadata): UploadTimelineAdResult {
            return UploadTimelineAdResult(
                timelineAd = timelineAd
            )
        }
    }
}
