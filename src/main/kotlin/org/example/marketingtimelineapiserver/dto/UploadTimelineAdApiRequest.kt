package org.example.marketingtimelineapiserver.dto

import java.util.UUID

data class UploadTimelineAdApiRequest(
    val influencerId: UUID,
    val advertisementId: Long
)
