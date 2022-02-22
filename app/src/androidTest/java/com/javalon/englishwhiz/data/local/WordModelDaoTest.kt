package com.javalon.englishwhiz.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.javalon.englishwhiz.data.WordModelDatabase
import com.javalon.englishwhiz.data.local.entity.Label
import com.javalon.englishwhiz.data.local.entity.Meaning
import com.javalon.englishwhiz.domain.model.WordModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordModelDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var wordModelDatabase: WordModelDatabase
    private lateinit var wordModelDao: WordModelDao

    @Before
    fun setUp() {
        wordModelDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WordModelDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        wordModelDao = wordModelDatabase.wordModelDao
    }

    @After
    fun tearDown() {
        wordModelDatabase.close()
    }

    @Test
    fun insertBookmark() = runBlocking {
        val meaning =
            Meaning("definition", "example", "speechPart", synonyms = listOf("synon", "yms"),
                labels = listOf(Label("label")
                ))
        val wordModelEntity = WordModel(listOf(meaning), "word", "23dfd31").toBookmarkEntity()
        wordModelDao.insertBookmark(wordModelEntity)
        val result = wordModelDao.getAllBookmark()
        assertThat(result).contains(wordModelEntity)
    }

    @Test
    fun deleteBookmark() = runBlocking {
        val meaning =
            Meaning("definition", "example", "speechPart", synonyms = listOf("synon", "yms"),
                labels = listOf(Label("label")
                ))
        val wordModelEntity = WordModel(listOf(meaning), "word", "23dfd31").toBookmarkEntity()
        wordModelDao.insertBookmark(wordModelEntity)
        wordModelDao.deleteBookmark(wordModelEntity)
        val result = wordModelDao.getAllBookmark()
        assertThat(result).doesNotContain(wordModelEntity)
    }

    @Test
    fun insertHistory() = runBlocking {
        val meaning =
            Meaning("definition", "example", "speechPart", synonyms = listOf("synon", "yms"),
                labels = listOf(Label("label")
                ))
        val wordModelEntity = WordModel(listOf(meaning), "word", "23dfd31").toHistoryEntity()
        wordModelDao.insertHistory(wordModelEntity)
        val result = wordModelDao.getAllHistory()
        assertThat(result).contains(wordModelEntity)
    }

    @Test
    fun deleteHistory() = runBlocking {
        val meaning =
            Meaning("definition", "example", "speechPart", synonyms = listOf("synon", "yms"),
                labels = listOf(Label("label")
                ))
        val wordModelEntity = WordModel(listOf(meaning), "word", "23dfd31").toHistoryEntity()
        wordModelDao.insertHistory(wordModelEntity)
        wordModelDao.deleteHistory(wordModelEntity)
        val result = wordModelDao.getAllHistory()
        assertThat(result).doesNotContain(wordModelEntity)
    }
}