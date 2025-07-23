package org.example.marketingtimelineapiserver.dto

import org.example.marketingtimelineapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus


data class DeleteTimelineAdResponseFromServer(
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null,
    val deletedRow: Int,
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun success(deletedRow: Int): DeleteTimelineAdResponseFromServer {
            return DeleteTimelineAdResponseFromServer(
                httpStatus = HttpStatus.OK,
                msaServiceErrorCode = MSAServiceErrorCode.OK,
                deletedRow = deletedRow
            )
        }

        fun error(
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String?,
            logics: String
        ): DeleteTimelineAdResponseFromServer {
            return DeleteTimelineAdResponseFromServer(
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics,
                deletedRow = 0
            )
        }
    }
}