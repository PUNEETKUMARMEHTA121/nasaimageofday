package com.example.nasaimageofday.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Image")
data class ApiImageResponse(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @SerializedName("date")
    var date: String?,

    @SerializedName("explanation")
    var explanation: String?,

    @SerializedName("hdurl")
    var hdurl: String?,

    @SerializedName("media_type")
    var mediaType: String?,

    @SerializedName("service_version")
    var serviceVersion: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("url")
    var url: String?
) {
    companion object {
        fun empty() = ApiImageResponse(0,null, null, null, null, null, null, null)
    }
}