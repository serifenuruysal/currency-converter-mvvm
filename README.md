## Currency Converter App

The application list all currencies. Each row has an input where you can enter any amount of money. When you tap on a currency row it should slide to the top and it's input becomes the first responder. When you’re changing the amount the app must simultaneously update the corresponding value for other currencies.
The app also  download and update rates every 1 second using API

## Libraries
## Libraries
*   [AndroidX](https://developer.android.com/jetpack/androidx)  Android Architecture Components: 
    ViewModel , 
    LiveData
*   [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0) 
*   [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
*   [OkHttp](http://square.github.io/okhttp/) 
*   [Retrofit](http://square.github.io/retrofit/)
*   [Gson](https://github.com/google/gson)
*   [Moshi](https://github.com/square/moshi)

## Approach of Clean Architecture for Android
The whole application is built based on the MVVM architectural pattern.There are 3 separated  layers in the project: Data, Domain, Presentation layer.

## Requirements &amp; configurations
#### Requirements
- JDK 8
- Android SDK API 29
- Kotlin Gradle plugin 1.3.61 *(it will be installed automatically when this project is synced)*

#### Configurations
- minSdkVersion=21
- targetSdkVersion=29

## Language
*   [Kotlin](https://kotlinlang.org/)

## Demo
![Currency Converter Demo](demo/demo.gif)


#### Serife Nur Uysal
