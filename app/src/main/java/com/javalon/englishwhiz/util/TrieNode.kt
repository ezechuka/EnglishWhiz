package com.javalon.englishwhiz.util

import com.javalon.englishwhiz.domain.model.WordModel

class TrieNode<Key>(var key: Key?, var parent: TrieNode<Key>?) {
    val children: HashMap<Key, TrieNode<Key>> = HashMap()
    var isValidWord = false
    var wordModel: WordModel? = null
}