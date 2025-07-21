package org.example.marketingtimelineapiserver.dto

import java.util.UUID

data class TimelineAdMetadata(
    val id: Long,
    val influencerId: UUID,
    val advertisementId: Long,
    val createdAt: Long,
    val lastModifiedAt: Long
) {
    companion object {
        fun fromEntity(entity: TimelineAdEntity): TimelineAdMetadata {
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
