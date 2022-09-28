package com.example.androidtesting.roobdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.androidtesting.getOrAwaitValue
import com.example.androidtesting.roomdb.Art
import com.example.androidtesting.roomdb.ArtDao
import com.example.androidtesting.roomdb.ArtDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ArtDatabase
    private lateinit var dao: ArtDao

    @Before
    fun setup() {

        //inMemoryDatabaseBuilder geçici db, test için uygun
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArtDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.artDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlockingTest {

        val exampleArt = Art("Ali", "Veli", 2020, "xyz.co", 1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runBlockingTest {

        val exampleArt = Art("Ali", "Veli", 2020, "xyz.co", 1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }

}