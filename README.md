@"
# SmartLibrary Application

SmartLibrary is a simple Android application developed for a college library.

## Features

- Library logo and information
- Books section
- Library Rules
- Contact Library
- Issue Book functionality
- Book issue confirmation
- Android notification after successful book issue
- Activity-to-Activity navigation using Intents
- Fragment-based navigation
- Logcat implementation

## Technologies Used

- Kotlin
- XML
- Android Studio
- Activities
- Fragments
- Intents
- Android Notifications

## Application Flow

Home Activity
- Books
- Library Rules
- Contact Library

Books → Select Book → Issue Book → Confirm Issue → Notification

## Project Structure

```text
app/
└── src/
    └── main/
        ├── java/com/example/smartlibrary/
        │   ├── MainActivity.kt
        │   ├── BooksFragment.kt
        │   ├── LibraryRulesFragment.kt
        │   ├── ContactLibraryFragment.kt
        │   └── IssueBookActivity.kt
        │
        ├── res/
        │   ├── layout/
        │   ├── drawable/
        │   └── values/
        │
        └── AndroidManifest.xml

        
