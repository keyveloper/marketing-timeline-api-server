package org.example.marketingtimelineapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketingtimelineapiserver.dto.GetTimelineAdsResult
import org.example.marketingtimelineapiserver.dto.UploadTimelineAdRequest
import org.example.marketingtimelineapiserver.dto.UploadTimelineAdResult
import org.example.marketingtimelineapiserver.enums.MSAServiceErrorCode
import org.example.marketingtimelineapiserver.enums.TimelineCursor
import org.example.marketingtimelineapiserver.exception.DeleteTimelineAdException
import org.example.marketingtimelineapiserver.exception.MSAServerException
import org.example.marketingtimelineapiserver.repository.TimelineRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TimelineService(
    private val timelineRepository: TimelineRepository
) {
    private val logger = KotlinLogging.logger {}

    fun getTimelineAds(
        influencerId: UUID,
        cursor: TimelineCursor,
        pivotTime: Long?
    ): GetTimelineAdsResult {
        try {
            return transaction {
                val timelineAds = when (cursor) {
                    TimelineCursor.INIT -> {
                        timelineRepository.findInitByInfluencer(
                            influencerId = influencerId
                        )
                    }
                    TimelineCursor.PREV -> {
                        if (pivotTime == null) {
                            throw MSAServerException(
                                httpStatus = HttpStatus.BAD_REQUEST,
                                msaServiceErrorCode = MSAServiceErrorCode.INTERNAL_SERVER_ERROR,
                                logics = "TimelineService.getTimelineAds",
                                message = "pivotTime is required for PREV cursor"
                            )
                        }
                        timelineRepository.findBeforeByInfluencer(
                            influencerId = influencerId,
                            pivotTime = pivotTime
                        )
                    }
                    TimelineCursor.NEXT -> {
                        if (pivotTime == null) {
                            throw MSAServerException(
                                httpStatus = HttpStatus.BAD_REQUEST,
                                msaServiceErrorCode = MSAServiceErrorCode.INTERNAL_SERVER_ERROR,
                                logics = "TimelineService.getTimelineAds",
                                message = "pivotTime is required for NEXT cursor"
                            )
                        }
                        val result = timelineRepository.findAfterByInfluencer(
                            influencerId = influencerId,
                            pivotTime = pivotTime
                        )
                        result.reversed()
                    }
                }

                val hasMore = timelineAds.size >= 10
                val nextCursor = if (hasMore && cursor != TimelineCursor.NEXT) {
                    timelineAds.lastOrNull()?.createdAt
                } else null

                GetTimelineAdsResult.of(
                    timelineAds = timelineAds,
                    nextCursor = nextCursor,
                    hasMore = hasMore
                )
            }
        } catch (e: MSAServerException) {
            throw e
        } catch (e: Exception) {
            logger.error(e) { "Failed to get timeline ads for influencer: $influencerId" }
            throw MSAServerException(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                msaServiceErrorCode = MSAServiceErrorCode.INTERNAL_SERVER_ERROR,
                logics = "TimelineService.getTimelineAds",
                message = "Failed to retrieve timeline ads"
            )
        }
    }

    fun saveTimelineAd(
        influencerId: UUID,
        advertisementId: Long
    ): UploadTimelineAdResult {
        try {
            return transaction {
                val exists = timelineRepository.existsByInfluencerIdAndAdvertisementId(
                    influencerId = influencerId,
                    advertisementId = advertisementId
                )

                if (exists) {
                    throw MSAServerException(
                        httpStatus = HttpStatus.BAD_REQUEST,
                        msaServiceErrorCode = MSAServiceErrorCode.SAVE_FAILED_FOR_DATABASE,
                        logics = "TimelineService.saveTimelineAd",
                        message = "Timeline ad already exists for this influencer and advertisement"
                    )
                }

                val metadata = timelineRepository.save(
                    UploadTimelineAdRequest.of(
                        influencerId = influencerId,
                        advertisementId = advertisementId
                    )
                )

                UploadTimelineAdResult.of(metadata)
            }
        } catch (e: MSAServerException) {
            throw e
        } catch (e: Exception) {
            logger.error(e) { "Failed to save timeline ad" }
            throw MSAServerException(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                msaServiceErrorCode = MSAServiceErrorCode.SAVE_FAILED_FOR_DATABASE,
                logics = "TimelineService.saveTimelineAd",
                message = "Failed to save timeline ad"
            )
        }
    }

    fun deleteTimelineAd(
        influencerId: UUID,
        advertisementId: Long
    ): Int {
        try {
            return transaction {
                val deleteCount = timelineRepository.deleteByInfluencerIdAndAdvertisementId(
                    influencerId = influencerId,
                    advertisementId = advertisementId
                )

                if (deleteCount == 0) {
                    throw DeleteTimelineAdException(
                        influencerId = influencerId,
                        advertisementId = advertisementId
                    )
                }

                deleteCount
            }
        } catch (e: MSAServerException) {
            throw e
        } catch (e: Exception) {
            logger.error(e) { "Failed to delete timeline ad" }
            throw MSAServerException(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                msaServiceErrorCode = MSAServiceErrorCode.INTERNAL_SERVER_ERROR,
                logics = "TimelineService.deleteTimelineAd",
                message = "Failed to delete timeline ad"
            )
        }
    }
}
