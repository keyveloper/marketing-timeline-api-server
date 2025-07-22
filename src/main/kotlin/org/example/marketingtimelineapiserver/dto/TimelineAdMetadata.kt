package org.example.marketingtimelineapiserver.dto

data class TimelineAdMetadata(
    val id: Long,
    val influencerId: String,
    val advertisementId: String,
    val createdAt: Long,
    val lastModifiedAt: Long
) {
    companion object {
        fun of(
            id: Long,
            influencerId: String,
            advertisementId: String,
            createdAt: Long,
            lastModifiedAt: Long
        ): TimelineAdMetadata {
            return TimelineAdMetadata(
                id = id,
                influencerId = influencerId,
                advertisementId = advertisementId,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt
            )
        }

        fun from(entity: TimelineAdEntity): TimelineAdMetadata {
            return TimelineAdMetadata(
                id = entity.id.value,
                influencerId = entity.influencerId,
                advertisementId = entity.advertisementId,
                createdAt = entity.createAt,
                lastModifiedAt = entity.lastModifiedAt
            )
        }
    }
}
