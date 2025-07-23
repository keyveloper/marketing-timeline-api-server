package org.example.marketingtimelineapiserver.controller

import org.example.marketingtimelineapiserver.dto.*
import org.example.marketingtimelineapiserver.service.TimelineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/timeline")
class TimelineController(
    private val timelineService: TimelineService
) {

    @GetMapping("/ads")
    fun getTimelineAds(
        @ModelAttribute request: GetTimelineAdsApiRequest
    ): ResponseEntity<GetTimelineAdsResponseFromServer> {
        val result = timelineService.getTimelineAds(
            influencerId = request.influencerId,
            cursor = request.cursor,
            pivotTime = request.pivotTime
        )

        return ResponseEntity.ok(
            GetTimelineAdsResponseFromServer.success(result)
        )
    }

    @PostMapping("/ads")
    fun uploadTimelineAd(
        @RequestBody request: UploadTimelineAdApiRequest
    ): ResponseEntity<UploadTimelineAdResponseFromServer> {
        val result = timelineService.saveTimelineAd(
            influencerId = request.influencerId,
            advertisementId = request.advertisementId
        )

        return ResponseEntity.ok(
            UploadTimelineAdResponseFromServer.success(result)
        )
    }

    @DeleteMapping("/ads")
    fun deleteTimelineAd(
        @RequestParam(value = "influencerId") influencerId: UUID,
        @RequestParam(value = "advertisementId") advertisementId: Long,
    ): ResponseEntity<DeleteTimelineAdResponseFromServer> {
        val deletedRow = timelineService.deleteTimelineAd(
            influencerId = influencerId,
            advertisementId = advertisementId
        )

        return ResponseEntity.ok(
            DeleteTimelineAdResponseFromServer.success(
                deletedRow
            )
        )
    }
}
