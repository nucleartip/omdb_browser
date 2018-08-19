**TMDB movie browser application.**

it sources all the api from 
https://www.themoviedb.org/documentation/api

**Provides following following functionality**

1. A movie browser, which display most popular movies
2. A detail view, which list more details about a particular movie

Movie detail can be opened upon clicking a movie tile in list.


**Design pattern:**

I have tried to implement this app using clean MVVM reactive pattern,
using Android data binding library along with RxJava, UI and Business logic has been seperated out
making testability easier.


**Testability**

you can find test codes under test folder, I have tested few classes, although I have not tested
the entire code base, but has written enough to demonstrate testability.

**Open source library usage**

 1. Retrolamba for lambda support.
 2. Rxjava, RxAndroid for reactive stream.
 3. Dagger, for dependency injection
 4. Glide, for downloading image over network along with transformations
    and caching.
 5. Retrofit along with google gsons and RxJava adapter, for handling network calls
 6. Room db, for all DB operations along with respective Rx adapters.
 

I have tried to write comments in most sections of code, it should provide major info
over api's, classes and there usages.

You can reach me back in case of any more information.