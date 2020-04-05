# touch
Android library, which allows to monitor raw touch events on the screen of the device with RxJava

 Current Branch | Branch  | Artifact Id | Build Status  | Maven Central |
|:--------------:|:-------:|:-----------:|:-------------:|:-------------:|
| :ballot_box_with_check: | [`RxJava2.x`](https://github.com/pwittchen/touch/tree/RxJava2.x) | `touch-rx2` | [![Build Status for RxJava2.x](https://img.shields.io/travis/pwittchen/touch/RxJava2.x.svg?style=flat-square)](https://travis-ci.org/pwittchen/touch) | ![Maven Central](https://img.shields.io/maven-central/v/com.github.pwittchen/touch-rx2.svg?style=flat-square) |

Contents
--------
- [Usage](#usage)
- [Example](#example)
- [Download](#download)
- [Tests](#tests)
- [Code style](#code-style)
- [Static code analysis](#static-code-analysis)
- [Release](#release)
- [References](#references)
- [License](#license)

Usage
-----

**Step 1**: Create `Touch` attribute and `Disposable` in the `Activity`:

```java
private Touch touch;
private Disposable disposable;
```

**Step 2**: Initialize `Touch` object and subscribe `Flowable`:

```java
@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  info = (TextView) findViewById(R.id.info);

  touch = new Touch();

  disposable = touch.observe()
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(touchEvent -> info.setText(touchEvent.toString()));
}
```

`TouchEvent` is a class with the following values:

```java
public enum TouchEvent {
  public float x() {}
  public float y() {}
  public TouchType type() {}
}
```

`TouchType` is an enum with the following values:

```java
public enum TouchType {
  UP, DOWN, MOVE
}
```

**Step 3**: override `dispatchTouchEvent(MotionEvent event)`:

```java
@Override public boolean dispatchTouchEvent(MotionEvent event) {
  touch.dispatchTouchEvent(event);
  return super.dispatchTouchEvent(event);
}
```

**Step 4**: dispose previously created `Disposable` when it's no longer needed:

```java
@Override protected void onPause() {
  super.onPause();
  if (disposable != null && !disposable.isDisposed()) {
    disposable.dispose();
  }
}
```

Example
-------

Exemplary application is located in `app` directory of this repository.

Download
--------

latest version: ![Maven Central](https://img.shields.io/maven-central/v/com.github.pwittchen/touch-rx2.svg?style=flat-square)

replace `x.y.z` with the latest version of the library

You can depend on the library through Maven:

```xml
<dependency>
    <groupId>com.github.pwittchen</groupId>
    <artifactId>touch-rx2</artifactId>
    <version>x.y.z</version>
</dependency>
```

or through Gradle:

```groovy
dependencies {
  compile 'com.github.pwittchen:touch-rx2:x.y.z'
}
```

Tests
-----

To execute unit tests run:

```
./gradlew test
```

Code style
----------

Code style used in the project is called `SquareAndroid` from Java Code Styles repository by Square available at: https://github.com/square/java-code-styles.

Static code analysis
--------------------

Static code analysis runs Checkstyle, PMD and Lint. It can be executed with command:

 ```
 ./gradlew check
 ```

Reports from analysis are generated in `library/build/reports/` directory.

Release
-------

To release the library, bump its version and call the following command:

```
./gradlew uploadArchives closeAndReleaseRepository
```

References
----------

- [better gesture detector project](https://github.com/Polidea/better-gesture-detector)
- [gesture project](https://github.com/pwittchen/gesture)
- [swipe project](https://github.com/pwittchen/swipe)
- [detecting swipe gesture in mobile application](http://blog.wittchen.biz.pl/detecting-swipe-gesture-in-mobile-application/)
- [dispatchTouchEvent(event) method in documentation](http://developer.android.com/reference/android/view/ViewGroup.html#dispatchTouchEvent(android.view.MotionEvent))
- [MotionEvent class in documentation](http://developer.android.com/reference/android/view/MotionEvent.html)

License
-------

    Copyright 2020 Piotr Wittchen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
