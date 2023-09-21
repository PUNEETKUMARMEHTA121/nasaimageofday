package com.example.nasaimageofday.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nasaimageofday.model.ApiImageResponse

@Dao
interface ImageDao {

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(image: ApiImageResponse):Long

    @Query("DELETE  FROM Image")
    fun deleteImage()
}
