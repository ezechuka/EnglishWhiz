package com.javalon.englishwhiz.util

import com.javalon.englishwhiz.domain.model.WordModel
import com.javalon.englishwhiz.presentation.home.WordState

class Trie<Key> {
    // root of a Trie always has the `key` and `parent` as null.
    private val root = TrieNode<Key>(null, null)

    /**
     * inserts the list into the Trie
     * @param: list - generic list contain items to be added
     * @param: wordModel - the wordModel which holds the info about the word passed as a list.
     */
    fun insert(list: List<Key>, wordModel: WordModel) {
        var current = root
        list.forEach { element ->
            if (current.children[element] == null) {
                current.children[element] = TrieNode(element, current)
            }
            current = current.children[element]!!
        }

        current.isValidWord = true
        current.wordModel = wordModel
    }

    /**
     * checks if list is contained the Trie
     * @param: list - generic list containing the items to be checked
     * @return: WordState - `true` if the list is contained in the Trie else `false`
     */
    fun contains(list: List<Key>): WordState {
        var current = root
        list.forEach { element ->
            val child = current.children[element] ?: return WordState(isContained = false)
            current = child
        }

        return WordState(current.wordModel, isContained = current.isValidWord)
    }

    /**
     * searches for other words that match the given prefix.
     * @param: prefix - list contain the prefix word.
     * @return list containing another list matching the required prefix.
     */
    fun prefixMatch(prefix: List<Key>): List<List<Key>> {
        var current = root

        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }

        return collections(prefix, current)
    }

    /**
     * helper function to `prefixMatch` which does the prefix matching
     * @param: prefix
     * @param: node
     * @return: List<List<Key>>
     */
    private fun collections(prefix: List<Key>, node: TrieNode<Key>?): List<List<Key>> {
        val results = mutableListOf<List<Key>>()
        if (node?.isValidWord == true) {
            results.add(prefix)
        }

        node?.children?.forEach { (key, trieNode) ->
            results.addAll(collections((prefix + key), trieNode))
        }

        return results
    }
}