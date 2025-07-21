package org.example.marketingtimelineapiserver.dto

import java.util.UUID

data class SaveTimelineAdRequest(
    val influencerId: UUID,
    val advertisementId: Long
) {
    companion object {
        fun of(
            influencerId: UUID,
            advertisementId: Long
        ): SaveTimelineAdRequest {
            return SaveTimelineAdRequest(
                influencerId = influencerId,
                advertisementId = advertisementId
            )
        }
    }
}
