# MVVMPostsApp
Modern Android sample app built with Kotlin, Jetpack Compose, MVVM + MVI-style state handling, offline caching, and dependency injection.

## Features

- Login screen with:
  - Email validation
  - Password length validation (8-15)
  - Persisted login session using DataStore
- Home screen with bottom navigation:
  - Posts
  - Favorites
- Posts:
  - Remote sync from `https://jsonplaceholder.typicode.com/posts`
  - Offline-first display from Room database
  - Favorite toggle on post card
  - Retry flow with internet connectivity check
  - Comments bottom sheet from `GET /posts/{post_id}/comments`
- Favorites:
  - Room-backed favorites list
  - Swipe to remove favorite
- UI states:
  - Loading / Success / Error
  - Empty states for posts and favorites

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- MVVM + MVI-style events/state/effects
- Hilt (DI)
- Retrofit + OkHttp
- Room (local cache)
- DataStore (session persistence)
- Coroutines + Flow
- Glide (image loading)

## Project Structure
```
app/src/main/java/com/example/systemassesment
├── data
│   ├── local
│   ├── mapper
│   ├── model
│   ├── remote
│   └── repository
├── di
├── ui
│   ├── components
│   ├── navigation
│   ├── screens
│   │   ├── favorites
│   │   ├── home
│   │   ├── login
│   │   └── posts
│   └── theme
└── utils
```

## Notes

- Posts are rendered from Room as the source of truth.
- On first successful fetch, posts are cached for offline usage.



