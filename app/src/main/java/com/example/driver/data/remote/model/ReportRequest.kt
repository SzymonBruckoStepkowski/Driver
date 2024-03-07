package com.example.driver.data.remote.model

import android.content.Context
import com.example.driver.common.formatToApiDateTimeString
import com.example.driver.domain.model.EventType
import com.example.driver.presentation.util.ComposeFileProvider
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

data class ReportRequest(
    val data: Date?,
    val mileage: Long?,
    val geo_long: String?,
    val geo_lat: String?,
    val driver_id: String?,
    val vehicle_id: String?,
    val trailer_id: String?,
    val operation_type: String?,
    val city: String?,
    val files: List<String>?
)

fun ReportRequest.toMultipartBody(context: Context, currentFiles: List<String>? = null): MultipartBody {
    val builder = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("data", data?.formatToApiDateTimeString().orEmpty())
        .addFormDataPart("mileage", mileage.toString())
        .addFormDataPart("driver_id", driver_id.orEmpty())
        .addFormDataPart("vehicle_id", vehicle_id.orEmpty())
        .addFormDataPart("operation_type", operation_type ?: EventType.UNKNOWN.name.lowercase())

    if (geo_long != null && geo_lat != null) {
        builder
            .addFormDataPart("geo_long", geo_long)
            .addFormDataPart("geo_lat", geo_lat)

        if (city != null) {
            builder
                .addFormDataPart("city", city)
        }
    }

    // add new files
    files?.filter { currentFiles?.contains(it) != true }?.forEach { filename ->
        builder.addFormDataPart(
            "files", filename,
            File(ComposeFileProvider.getFilePath(context) + filename).asRequestBody()
        )
    }

    // delete removed files
    currentFiles?.filter { files?.contains(it) != true }?.forEach { filename ->
        builder.addFormDataPart("files-", filename)
    }

    return builder.build()
}