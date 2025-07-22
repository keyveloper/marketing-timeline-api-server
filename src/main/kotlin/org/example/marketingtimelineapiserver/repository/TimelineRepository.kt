package org.example.marketingtimelineapiserver.repository

import org.example.marketingtimelineapiserver.dto.SaveTimelineAdRequest
import org.example.marketingtimelineapiserver.dto.TimelineAdEntity
import org.example.marketingtimelineapiserver.dto.TimelineAdMetadata
import org.example.marketingtimelineapiserver.table.TimelineAdTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class TimelineRepository {

    fun findByInfluencerIdWithCursor(
        influencerId: String,
        cursor: Long?,
        limit: Int
    ): List<TimelineAdMetadata> {
        return transaction {
            val query = if (cursor == null) {
                TimelineAdEntity.find {
                    TimelineAdTable.influencerId eq influencerId
                }
            } else {
                TimelineAdEntity.find {
                    (TimelineAdTable.influencerId eq influencerId) and
                            (TimelineAdTable.id less cursor)
                }
            }

            query.orderBy(TimelineAdTable.id to SortOrder.DESC)
                .limit(limit)
                .map { TimelineAdMetadata.from(it) }
        }
    }

    fun save(request: SaveTimelineAdRequest): TimelineAdMetadata {
        return transaction {
            val entity = TimelineAdEntity.new {
                influencerId = request.influencerId
                advertisementId = request.advertisementId
            }
            TimelineAdMetadata.from(entity)
        }
    }

    fun existsByInfluencerIdAndAdvertisementId(
        influencerId: String,
        advertisementId: String
    ): Boolean {
        return transaction {
            TimelineAdEntity.find {
                (TimelineAdTable.influencerId eq influencerId) and
                        (TimelineAdTable.advertisementId eq advertisementId)
            }.count() > 0
        }
    }

    fun deleteByInfluencerIdAndAdvertisementId(
        influencerId: String,
        advertisementId: String
    ): Boolean {
        return transaction {
            val entity = TimelineAdEntity.find {
                (TimelineAdTable.influencerId eq influencerId) and
                        (TimelineAdTable.advertisementId eq advertisementId)
            }.firstOrNull()

            entity?.delete()
            entity != null
        }
    }
}
