package com.javalon.englishwhiz.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.javalon.englishwhiz.data.DictionaryDatabase
import com.javalon.englishwhiz.data.local.DictionaryDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DictionaryRepositoryTest {

    private lateinit var dao: DictionaryDao
    private lateinit var dictionaryRepository: DictionaryRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        dao = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DictionaryDatabase::class.java,
            "wordDb"
        )
            .createFromAsset("wordset/wordDB")
            .allowMainThreadQueries()
            .build().dictionaryDao
        dictionaryRepository = DictionaryRepository(dao)
    }

    @Test
    fun `prefix matcher returns list of match when query matches is in database`() = runBlocking {
        dictionaryRepository.prefixMatch("lo").test {
            val result = awaitItem()
            val data = result.data
            assertThat(data).isNotEmpty()
        }
    }
}