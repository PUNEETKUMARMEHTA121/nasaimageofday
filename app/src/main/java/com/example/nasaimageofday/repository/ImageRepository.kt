package com.example.nasaimageofday.repository

import com.example.nasaimageofday.api.RetrofitInstance
import com.example.nasaimageofday.db.ImageDatabase
import com.example.nasaimageofday.model.ApiImageResponse
import java.io.IOException
import com.example.nasaimageofday.model.Result

class ImageRepository(
    val db: ImageDatabase
) {

    suspend fun getImageOfDay(): Result<ApiImageResponse> {
        try {
            val response = RetrofitInstance.api.getImageOfDay()
            if (response.isSuccessful) {
                val apiImageResponse = response.body()
                if (apiImageResponse != null) {
                    // Successfully received data

                    return Result.Success(apiImageResponse)
                }
            }
            // Handle any other non-successful responses here
            return Result.Error(Exception("Error: ${response.code()} ${response.message()}"))
        } catch (e: IOException) {
            // Handle network errors
            return Result.Error(e)
        }
    }

    suspend fun saveImage(instance: ApiImageResponse) {
        db.getImageDao().deleteImage()
        db.getImageDao().insert(instance)
    }

}

