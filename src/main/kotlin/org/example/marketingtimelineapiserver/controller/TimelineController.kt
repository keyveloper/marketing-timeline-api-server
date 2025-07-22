package org.example.marketingtimelineapiserver.controller

import org.example.marketingtimelineapiserver.dto.GetTimelineAdsApiRequest
import org.example.marketingtimelineapiserver.dto.GetTimelineAdsResponseFromServer
import org.example.marketingtimelineapiserver.service.TimelineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/timeline")
class TimelineController(
    private val timelineService: TimelineService
) {

    @GetMapping("/ads")
    fun getTimelineAds(
        @RequestParam influencerId: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") limit: Int
    ): ResponseEntity<GetTimelineAdsResponseFromServer> {
        val result = timelineService.getTimelineAds(
            influencerId = influencerId,
            cursor = cursor,
            limit = limit
        )

        return ResponseEntity.ok(
            GetTimelineAdsResponseFromServer.success(result)
        )
    }

    @PostMapping("/ads")
    fun saveTimelineAd(
        @RequestParam influencerId: String,
        @RequestParam advertisementId: String
    ): ResponseEntity<Map<String, Any>> {
        val metadata = timelineService.saveTimelineAd(
            influencerId = influencerId,
            advertisementId = advertisementId
        )

        return ResponseEntity.ok(
            mapOf(
                "success" to true,
                "data" to metadata
            )
        )
    }

    @DeleteMapping("/ads")
    fun deleteTimelineAd(
        @RequestParam influencerId: String,
        @RequestParam advertisementId: String
    ): ResponseEntity<Map<String, Any>> {
        timelineService.deleteTimelineAd(
            influencerId = influencerId,
            advertisementId = advertisementId
        )

        return ResponseEntity.ok(
            mapOf(
                "success" to true,
                "message" to "Timeline ad deleted successfully"
            )
        )
    }
}
