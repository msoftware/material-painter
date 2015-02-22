Material Painter
==========================

>Generate a color pallette based on an image.

### Are you a designer?

This might be interesting for you. With Material Painter you'll be able to extract prominent colors from an image.
You'll be able to see 6 different versions

- Vibrant
- Vibrant dark
- Vibrant light
- Muted
- Muted dark
- Muted light

- **`android`** all the Android related code resides here
    - `src/main/java` code shared between all the build variants
    - `src/main/res` contains the assets packaged within the app
    - `src/debug/java` code to be used uniquely for the debug build
    - `src/androidTest/java` instrumentation tests, using the [Espresso framework](https://code.google.com/p/android-test-kit/wiki/Espresso) They need to run on a connected device

- **`core`** all the code without an explicit Android dependecy resides here
    - `src/main/java` plain Java classes
    - `src/unit/java` unit tests for the code in the `core/src/main`
    - `src/integration/java` integrations tests for the code in the `core/src/main`

- **`android-tests`** unit tests for the `android` module
    - `src/unit/java` unit tests using Android shadows from [Robolectric](http://robolectric.org/). They can be run on the JVM, which is faster than running on a device
    - `src/integration/java` collection of integration tests to be run on the JVM

### Building the app
The app builds using [Gradle](http://www.gradle.org/). An executable wrapper for Gradle is provided within the codebase (`gradlew` and `gradlew.bat`), so you don't need to install Gradle locally to build the app.

To build and install the debug version on the connected device just invoke the gradle wrapper script from the root project folder:

    ./gradlew clean installDebug


Build and install the release version on the connected device:

    ./gradlew clean installRelease


Run all the unit tests in the project (also generates all the apks in `./android/build/apk/`):

    ./gradlew clean build


**NOTE: every task in gradle can be identified (and run) using its acronym:**

- `installDebug` -> `iD`
- `installRelease` -> `iR`

e.g. `./gradlew clean cATED`
