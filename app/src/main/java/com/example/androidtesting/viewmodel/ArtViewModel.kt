package com.example.androidtesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtesting.model.ImageResponse
import com.example.androidtesting.repo.ArtRepositoryInterface
import com.example.androidtesting.roomdb.Art
import com.example.androidtesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
) : ViewModel() {

    //ArtFragment
    val artList = repository.getArt()

    //ImageApiFragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    //ArtDetailFragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Enter all fields", null))
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Year must be number", null))
            return
        }

        val art = Art(name, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchImage: String) {
        if (searchImage.isEmpty()) return

        images.value = Resource.loading(null)

        //suspend fonksiyon olduğu için scope kullanıyoruz
        viewModelScope.launch {
            images.value = repository.searchImage(searchImage)
        }
    }

}