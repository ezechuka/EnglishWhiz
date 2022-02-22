package com.javalon.englishwhiz.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.javalon.englishwhiz.data.DictionaryDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DictionaryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var dictionaryDatabase: DictionaryDatabase
    private lateinit var dictionaryDao: DictionaryDao

    @Before
    fun setup() {
        dictionaryDatabase = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext, DictionaryDatabase::class.java, "wordDb"
        ).createFromAsset("wordset/wordDB")
            .allowMainThreadQueries()
            .build()
        dictionaryDao = dictionaryDatabase.dictionaryDao
    }

    @After
    fun teardown() {
        dictionaryDatabase.close()
    }

    @Test
    fun `prefix matcher returns list of match when query is in database`() = runBlocking {
        val matches = dictionaryDao.prefixMatch("comr")
        assertThat(matches.size).isGreaterThan(0)
    }

    @Test
    fun `prefix matcher returns empty list of match when query is not in database`() = runBlocking {
        val matches = dictionaryDao.prefixMatch("xcomrx")
        assertThat(matches.size).isEqualTo(0)
    }

    @Test
    fun `searcher returns result when query is in database`() = runBlocking {
        val matches = dictionaryDao.search("comrade")
        assertThat(matches).isNotNull()
    }

    @Test
    fun `searcher returns no result when query is not in database`() = runBlocking {
        val matches = dictionaryDao.search("xcomradex")
        assertThat(matches).isNull()
    }
}