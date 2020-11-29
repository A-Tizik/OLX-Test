# OLX-Test

Repository for OLX Code Challenge

### Technical desicions taken, and a short explanation why

#### Overall stack
MVVM approach is applied in the app, with Views just observing the flow of data, and delegating processing of user input to ViewModels
I'm using Repository abstraction for managing incoming data which is injected into ViewModels directly, but for more complex cases
we can also employ UseCase abstraction.

#### Network
Retrofit as it's the default, widely used abstraction in Android community for REST APIs.
Other option here could a Ktor client which is the another widely used option for Kotlin projects

#### Serialization
Kotlinx.Serialization is used here because it avoids annotation processing(it's instead a compiler plugin),
but still retains all the benefits of generated converters as opposed to reflection

#### Asynchony/Reactivity
Kotlinx.Coroutines with Flow is used because it's more closely aligned with Kotlin language as opposed to RxJava,
but both options are fine and do their job just as well.

#### RecyclerView
Groupie is used here as a simple abstraction for RecylerView adapter management, for better separation of concerns
compared to manual ViewHolder/Adapter code

#### Dependency Injection
Dagger as the solution that provides the most compile-time safety, with Hilt for easier usage in Android.
For such simple projects manual constructor dependency injection is also perfectly fine

#### Screens
Fragments were used as a screen abstraction, and the app follows single activity approach as well.
While Fragments were really bad in the past, today they are okay. Alternatives would be libraries like
Conductor which also provide better navigation integration.
Navigation component can also be used, but it has a mixed reviews in dev community and is unnecessary for small projects



