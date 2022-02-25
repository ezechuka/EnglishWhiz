## EnglishWhiz - Dictionary App with Compose.
EnglishWhiz features fast word lookup using Modern Android tools including Compose. With over 177k words available in its offline database built with simple & minimal user interface in mind.
The data used in this project was made available by this awesome open-source [repository](https://github.com/wordset/wordset-dictionary) (sad to see was abandoned :smiling_face_with_tear:).
EnglishWhiz offers some features which includes:
* Bookmark
* Text-to-speech
* Search history
* Word suggestions, etc.

## Libraries/Tools used
* [Room persistence library](https://developer.android.com/jetpack/androidx/releases/room)
* [Gson](https://github.com/google/gson)
* [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [MVVM](https://developer.android.com/jetpack/guide?gclid=Cj0KCQiAuvOPBhDXARIsAKzLQ8HZzKJ1ZNhu19088CRAy_5AkXyqkggycLhH85QWnWUDn_OoWmwIUWsaArbwEALw_wcB&gclsrc=aw.ds)
* [Compose](https://developer.android.com/jetpack/compose?gclid=Cj0KCQiAuvOPBhDXARIsAKzLQ8GSVc9ZODy0aKzeKMDFOeCbLggMbMvmgRTR7faxei96FA3tol2O7nEaAh72EALw_wcB&gclsrc=aw.ds)
* [Truth](https://truth.dev/)

## Search Feature
Originally, I intended on using Trie data structure for prefix matching/searching but due to the time it takes to setup the Trie made it not feasible. So instead I had to go with
SQLite query for prefix matching/searching which was less efficient than Trie but hopefully in the future the query can be made faster with SQL FTS feature.

## TODO
* Dark mode.
* Faster prefix matching/searching.
* Words linking.


### If you like this project, leave a Star ⭐ to receive updates on your GitHub dashboard.
