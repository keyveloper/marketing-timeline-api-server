package org.example.marketingtimelineapiserver.dto

import org.example.marketingtimelineapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetTimelineAdsResponseFromServer(
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null,
    val result: GetTimelineAdsResult? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun success(result: GetTimelineAdsResult): GetTimelineAdsResponseFromServer {
            return GetTimelineAdsResponseFromServer(
                httpStatus = HttpStatus.OK,
                msaServiceErrorCode = MSAServiceErrorCode.OK,
                result = result
            )
        }

        fun error(
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String?,
            logics: String
        ): GetTimelineAdsResponseFromServer {
            return GetTimelineAdsResponseFromServer(
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
