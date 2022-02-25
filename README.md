## EnglishWhiz - Dictionary App with Compose.
<p>
    <img src="https://github.com/ezechuka/EnglishWhiz/blob/main/asset/asset1.png" width="200px" height="auto"/>
    <img src="https://github.com/ezechuka/EnglishWhiz/blob/main/asset/asset2.png" width="200px" width="200px" height="auto" hspace="10"/>
    <img src="https://github.com/ezechuka/EnglishWhiz/blob/main/asset/asset6.png" width="200px" width="200px" height="auto" />
</p>

<p>
    <img src="https://github.com/ezechuka/EnglishWhiz/blob/main/asset/asset3.png" width="200px" height="auto"/>
    <img src="https://github.com/ezechuka/EnglishWhiz/blob/main/asset/asset4.png" width="200px" width="200px" height="auto" hspace="10"/>
    <img src="https://github.com/ezechuka/EnglishWhiz/blob/main/asset/asset5.png" width="200px" width="200px" height="auto" />
</p>

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
The search/prefeix matching was implemented with SQLite pattern matching strategy. Although using Trie data structure would be very efficient but the drawaback was the time & memory to setup making it not feasible. However, the feature can be improved using SQL FTS.

## TODO
* Dark mode.
* Faster prefix matching/searching.
* Words linking.


## If you like this project, leave a ⭐ to receive updates on your GitHub dashboard.
