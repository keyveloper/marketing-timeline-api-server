package org.example.marketingtimelineapiserver.table

import org.jetbrains.exposed.sql.Column
import java.util.UUID

object TimelineAdTable : BaseDateLongIdTable("timeline_ad") {
    val influencerId: Column<UUID> = uuid("influencer_id")
    val advertisementId: Column<Long> = long("advertisement_id")

    init {
        uniqueIndex(influencerId, advertisementId)
    }
}
