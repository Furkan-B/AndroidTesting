package com.example.androidtesting.repo

import androidx.lifecycle.LiveData
import com.example.androidtesting.model.ImageResponse
import com.example.androidtesting.roomdb.Art
import com.example.androidtesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>

}