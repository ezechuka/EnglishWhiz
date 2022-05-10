package com.javalon.englishwhiz.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.javalon.englishwhiz.data.repository.WordRepository
import com.javalon.englishwhiz.presentation.home.WordModelViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WordModelViewModelUnitTest {

    @Mock
    private lateinit var wordRepository: WordRepository

    private lateinit var fakeDictionaryRepo: FakeDictionaryRepository

    private lateinit var viewModel: WordModelViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fakeDictionaryRepo = FakeDictionaryRepository()
        viewModel = WordModelViewModel(wordRepository, fakeDictionaryRepo)
    }

    @Test
    fun `viewmodel should delegate searching call to dictionary repository and return an actual result`() {
        runBlocking {
            viewModel.searcher("comrade")
            assertThat(meaning).isEqualTo(viewModel.state.value?.meanings?.get(0))
        }
    }

    @Test
    fun `viewmodel should delegate prefix matching call to dictionary repository and return success when matches is in database`() {
        runBlocking {
            viewModel.prefixMatcher("comr")
            assertThat(dictionaryEntity.word).isEqualTo(viewModel.matches.value.first())
        }
    }

}