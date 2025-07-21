package org.example.marketingtimelineapiserver.dto

import java.util.UUID

data class UploadTimelineAdRequest(
    val influencerId: UUID,
    val advertisementId: Long
) {
    companion object {
        fun of(
            influencerId: UUID,
            advertisementId: Long
        ): UploadTimelineAdRequest {
            return UploadTimelineAdRequest(
                influencerId = influencerId,
                advertisementId = advertisementId
            )
        }
    }
}
