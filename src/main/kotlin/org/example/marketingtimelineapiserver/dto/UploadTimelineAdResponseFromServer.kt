package org.example.marketingtimelineapiserver.dto

import org.example.marketingtimelineapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class UploadTimelineAdResponseFromServer(
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null,
    val result: UploadTimelineAdResult? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun success(result: UploadTimelineAdResult): UploadTimelineAdResponseFromServer {
            return UploadTimelineAdResponseFromServer(
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
        ): UploadTimelineAdResponseFromServer {
            return UploadTimelineAdResponseFromServer(
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
