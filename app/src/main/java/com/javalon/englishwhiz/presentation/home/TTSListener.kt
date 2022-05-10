package com.javalon.englishwhiz.presentation.home

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class TTSListener(context: Context, private val onSpeechCompleted: () -> Unit) :
    DefaultLifecycleObserver {
    private var onInit = false
    private var textToSpeechEngine: TextToSpeech

    init {
        textToSpeechEngine = TextToSpeech(context, { status ->
            if (status == TextToSpeech.SUCCESS)
                onInit = true
        }, "com.google.android.tts")
    }

    fun speak(text: String) {
        if (onInit) {
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
        }
    }

    fun stop() {
        textToSpeechEngine.stop()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        textToSpeechEngine.setOnUtteranceProgressListener(object :
            UtteranceProgressListener() {

            override fun onError(p0: String?) {
            }

            override fun onStart(p0: String?) {

            }

            override fun onDone(p0: String?) {
                onSpeechCompleted()
            }
        })
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        textToSpeechEngine.stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        textToSpeechEngine.shutdown()
    }
}