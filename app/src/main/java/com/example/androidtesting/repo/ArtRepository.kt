package com.example.androidtesting.repo

import androidx.lifecycle.LiveData
import com.example.androidtesting.api.RetrofitAPI
import com.example.androidtesting.model.ImageResponse
import com.example.androidtesting.roomdb.Art
import com.example.androidtesting.roomdb.ArtDao
import com.example.androidtesting.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertAll(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteAll(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {

            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: run {
                    Resource.error("Error", null)
                }
            } else {
                Resource.error("Error", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data", null)
        }
    }
}