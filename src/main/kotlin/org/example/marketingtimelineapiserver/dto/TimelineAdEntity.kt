package org.example.marketingtimelineapiserver.dto

import org.example.marketingtimelineapiserver.table.TimelineAdTable
import org.jetbrains.exposed.dao.id.EntityID

class TimelineAdEntity(id: EntityID<Long>): BaseDateEntity(id, TimelineAdTable) {
    companion object: BaseDateEntityAutoUpdate<TimelineAdEntity>(TimelineAdTable)

    var influencerId: String by TimelineAdTable.influencerId
    var advertisementId: String by TimelineAdTable.advertisementId
}
