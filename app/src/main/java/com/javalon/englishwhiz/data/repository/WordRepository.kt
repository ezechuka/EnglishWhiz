package com.javalon.englishwhiz.data.repository

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.javalon.englishwhiz.data.local.WordModelDao
import com.javalon.englishwhiz.data.local.entity.WordModelDto
import com.javalon.englishwhiz.data.local.entity.WordModelEntity
import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.domain.repository.BaseRepository
import com.javalon.englishwhiz.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

const val WORDSET_SET = "wordset"
const val TAG = "WordRepository"

class WordRepository @Inject constructor(
    private val appContext: Context,
    private val objectMapper: ObjectMapper,
    private val wordModelDao: WordModelDao
) : BaseRepository {

    override fun readFromJsonStream(): Flow<Resource<MutableMap<String, List<WordModel>>>> = flow {
        val wordAssets = appContext.assets.list(WORDSET_SET)
        val wordMap = mutableMapOf<String, List<WordModel>>()
        emit(Resource.Loading(data = wordMap))
        wordAssets?.forEach { wordAsset ->
            try {
                var dictJson = ""
                val wordList = mutableListOf<WordModelDto>()
                val inputStream = appContext.assets.open("${WORDSET_SET}/$wordAsset")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                dictJson = String(buffer, charset("UTF-8"))
                val jsonObject = JSONObject(dictJson)
                val iterator = jsonObject.keys()
                while (iterator.hasNext()) {
                    val key = iterator.next()
                    val value = jsonObject.get(key) as JSONObject
//                        val word = gson.fromJson(value.toString(), WordModelDto::class.java)
                    val word = objectMapper.readValue(value.toString(), WordModelDto::class.java)
                    wordList.add(word)
                }

                wordMap[wordAsset.substring(0, wordAsset.indexOf('.'))] =
                    wordList.map { it.toWordModel() }
            } catch (ioException: IOException) {
                Log.e(TAG, ioException.toString())
                ioException.printStackTrace()
                emit(
                    Resource.Error(
                        data = wordMap,
                        message = ioException.message ?: "Couldn't read from assets file."
                    )
                )
            }
        }
        emit(Resource.Success(data = wordMap))
    }

    override fun insertWordModel(wordModelEntity: WordModelEntity): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(false))
        wordModelDao.insertWordModel(wordModelEntity)
        emit(Resource.Success(true))
    }

    override fun getAllBookmark(): Flow<Resource<List<WordModelEntity>>> = flow {
        emit(Resource.Loading())
        val result = wordModelDao.getAllBookmark()
        emit(Resource.Success(data = result))
    }
}