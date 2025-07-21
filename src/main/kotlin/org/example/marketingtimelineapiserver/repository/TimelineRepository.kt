package org.example.marketingtimelineapiserver.repository

import org.example.marketingtimelineapiserver.dto.UploadTimelineAdRequest
import org.example.marketingtimelineapiserver.dto.TimelineAdEntity
import org.example.marketingtimelineapiserver.dto.TimelineAdMetadata
import org.example.marketingtimelineapiserver.table.TimelineAdTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TimelineRepository {

    companion object {
        private const val PAGE_SIZE = 10
    }

    fun findInitByInfluencer(
        influencerId: UUID
    ): List<TimelineAdMetadata> {
        return TimelineAdEntity.find {
            TimelineAdTable.influencerId eq influencerId
        }
            .orderBy(TimelineAdTable.createdAt to SortOrder.DESC)
            .limit(PAGE_SIZE)
            .map { TimelineAdMetadata.fromEntity(it) }
    }

    fun findBeforeByInfluencer(
        influencerId: UUID,
        pivotTime: Long
    ): List<TimelineAdMetadata> {
        return TimelineAdEntity.find {
            (TimelineAdTable.influencerId eq influencerId) and
                    (TimelineAdTable.createdAt less pivotTime)
        }
            .orderBy(TimelineAdTable.createdAt to SortOrder.DESC)
            .limit(PAGE_SIZE)
            .map { TimelineAdMetadata.fromEntity(it) }
    }

    fun findAfterByInfluencer(
        influencerId: UUID,
        pivotTime: Long
    ): List<TimelineAdMetadata> {
        return TimelineAdEntity.find {
            (TimelineAdTable.influencerId eq influencerId) and
                    (TimelineAdTable.createdAt greater pivotTime)
        }
            .orderBy(TimelineAdTable.createdAt to SortOrder.ASC)
            .limit(PAGE_SIZE)
            .map { TimelineAdMetadata.fromEntity(it) }
    }

    fun save(request: UploadTimelineAdRequest): TimelineAdMetadata {
        val entity = TimelineAdEntity.new {
            influencerId = request.influencerId
            advertisementId = request.advertisementId
        }
        return TimelineAdMetadata.fromEntity(entity)
    }

    fun existsByInfluencerIdAndAdvertisementId(
        influencerId: UUID,
        advertisementId: Long
    ): Boolean {
        return TimelineAdEntity.find {
            (TimelineAdTable.influencerId eq influencerId) and
                    (TimelineAdTable.advertisementId eq advertisementId)
        }.count() > 0
    }

    fun deleteByInfluencerIdAndAdvertisementId(
        influencerId: UUID,
        advertisementId: Long
    ): Boolean {
        val entity = TimelineAdEntity.find {
            (TimelineAdTable.influencerId eq influencerId) and
                    (TimelineAdTable.advertisementId eq advertisementId)
        }.firstOrNull()

        entity?.delete()
        return entity != null
    }
}
