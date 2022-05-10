package com.javalon.englishwhiz.presentation

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.javalon.englishwhiz.data.DictionaryDatabase
import com.javalon.englishwhiz.data.WordModelDatabase
import com.javalon.englishwhiz.data.local.DictionaryDao
import com.javalon.englishwhiz.data.local.WordModelDao
import com.javalon.englishwhiz.data.local.entity.Label
import com.javalon.englishwhiz.data.local.entity.Meaning
import com.javalon.englishwhiz.data.repository.DictionaryRepository
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.presentation.home.WordModelViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordModelViewModelTest {

    private lateinit var dictionaryDatabase: DictionaryDatabase
    private lateinit var wordModelDatabase: WordModelDatabase
    private lateinit var dictionaryDao: DictionaryDao
    private lateinit var wordModelDao: WordModelDao
    private lateinit var wordRepository: WordRepository
    private lateinit var dictionaryRepository: DictionaryRepository
    private lateinit var wordModelViewModel: WordModelViewModel

    @Before
    fun setUp() {
        dictionaryDatabase = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(), DictionaryDatabase::class.java,
            "wordDb"
        )
            .createFromAsset("wordset/wordDB")
            .allowMainThreadQueries()
            .build()
        wordModelDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), WordModelDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dictionaryDao = dictionaryDatabase.dictionaryDao
        wordModelDao = wordModelDatabase.wordModelDao
        dictionaryRepository = DictionaryRepository(dictionaryDao)
        wordRepository = WordRepository(wordModelDao)
        wordModelViewModel = WordModelViewModel(wordRepository, dictionaryRepository)
    }

    @After
    fun teardown() {
        wordModelDatabase.close()
    }

    @Test
    fun `onInsertHistory`() = runBlocking {
        val meaning =
            Meaning(
                "definition", "example", "speechPart", synonyms = listOf("synon", "yms"),
                labels = listOf(
                    Label("label")
                )
            )
        val wordModel = WordModel(listOf(meaning), "word", "23dfd31")
        wordModelViewModel.insertHistory(wordModel)
        with(wordModelDao.getAllHistory()) {
            assertThat(this.size).isEqualTo(1)
        }
    }

    @Test
    fun `onInsertBookmark`() = runBlocking {
        val meaning =
            Meaning(
                "definition", "example", "speechPart", synonyms = listOf("synon", "yms"),
                labels = listOf(
                    Label("label")
                )
            )
        val wordModel = WordModel(listOf(meaning), "word", "23dfd31")
        wordModelViewModel.insertBookmark(wordModel)
        with(wordModelDao.getAllBookmark()) {
            assertThat(this.size).isEqualTo(1)
        }
    }
}