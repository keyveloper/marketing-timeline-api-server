package org.example.marketingtimelineapiserver.dto

import org.example.marketingtimelineapiserver.table.TimelineAdTable
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class TimelineAdEntity(id: EntityID<Long>): BaseDateEntity(id, TimelineAdTable) {
    companion object: BaseDateEntityAutoUpdate<TimelineAdEntity>(TimelineAdTable)

    var influencerId: UUID by TimelineAdTable.influencerId
    var advertisementId: Long by TimelineAdTable.advertisementId
}
