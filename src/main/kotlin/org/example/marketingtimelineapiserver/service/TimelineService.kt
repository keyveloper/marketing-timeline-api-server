package org.example.marketingtimelineapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketingtimelineapiserver.dto.GetTimelineAdsResult
import org.example.marketingtimelineapiserver.dto.SaveTimelineAdRequest
import org.example.marketingtimelineapiserver.dto.TimelineAdMetadata
import org.example.marketingtimelineapiserver.enums.MSAServiceErrorCode
import org.example.marketingtimelineapiserver.exception.MSAServerException
import org.example.marketingtimelineapiserver.repository.TimelineRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class TimelineService(
    private val timelineRepository: TimelineRepository
) {
    private val logger = KotlinLogging.logger {}

    fun getTimelineAds(
        influencerId: String,
        cursor: Long?,
        limit: Int
    ): GetTimelineAdsResult {
        try {
            val timelineAds = timelineRepository.findByInfluencerIdWithCursor(
                influencerId = influencerId,
                cursor = cursor,
                limit = limit + 1
            )

            val hasMore = timelineAds.size > limit
            val resultList = if (hasMore) timelineAds.dropLast(1) else timelineAds
            val nextCursor = if (hasMore) resultList.lastOrNull()?.id else null

            return GetTimelineAdsResult.of(
                timelineAds = resultList,
                nextCursor = nextCursor,
                hasMore = hasMore
            )
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
        influencerId: String,
        advertisementId: String
    ): TimelineAdMetadata {
        try {
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

            return timelineRepository.save(
                SaveTimelineAdRequest.of(
                    influencerId = influencerId,
                    advertisementId = advertisementId
                )
            )
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
        influencerId: String,
        advertisementId: String
    ): Boolean {
        try {
            val deleted = timelineRepository.deleteByInfluencerIdAndAdvertisementId(
                influencerId = influencerId,
                advertisementId = advertisementId
            )

            if (!deleted) {
                throw MSAServerException(
                    httpStatus = HttpStatus.NOT_FOUND,
                    msaServiceErrorCode = MSAServiceErrorCode.NOT_FOUND_TIMELINE_AD,
                    logics = "TimelineService.deleteTimelineAd",
                    message = "Timeline ad not found"
                )
            }

            return true
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
