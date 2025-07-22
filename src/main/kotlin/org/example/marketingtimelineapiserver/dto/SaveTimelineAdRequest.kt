package org.example.marketingtimelineapiserver.dto

data class SaveTimelineAdRequest(
    val influencerId: String,
    val advertisementId: String
) {
    companion object {
        fun of(
            influencerId: String,
            advertisementId: String
        ): SaveTimelineAdRequest {
            return SaveTimelineAdRequest(
                influencerId = influencerId,
                advertisementId = advertisementId
            )
        }
    }
}
