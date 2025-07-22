package org.example.marketingtimelineapiserver.table

import org.jetbrains.exposed.sql.Column

object TimelineAdTable : BaseDateLongIdTable("timeline_ad") {
    val influencerId: Column<String> = varchar("influencer_id", 36)
    val advertisementId: Column<String> = varchar("advertisement_id", 36)

    init {
        uniqueIndex(influencerId, advertisementId)
    }
}
