package org.example.marketingtimelineapiserver.exception

import org.example.marketingtimelineapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus
import java.util.UUID

class DeleteTimelineAdException(
    influencerId: UUID,
    advertisementId: Long,
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.NOT_FOUND_TIMELINE_AD,
    override val logics: String = "TimelineService.deleteTimelineAd",
    override val message: String = "Timeline ad not found for influencerId: $influencerId, advertisementId: $advertisementId"
) : MSAServerException(
    httpStatus = httpStatus,
    msaServiceErrorCode = msaServiceErrorCode,
    logics = logics,
    message = message
)
