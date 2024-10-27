# keepachangelog parser

This library implements a simple parser for [keepachangelog](https://keepachangelog.com)-formatted `CHANGELOG.md` files. Changelogs tracked in this human-readable format become machine-parseable and thus much more usable for fun things like analysis and involving the changelog in the release build process.

[![Javadocs](https://javadoc.io/badge/cx.cad.keepachangelog/changelog-parser.svg)](https://javadoc.io/doc/cx.cad.keepachangelog/changelog-parser)
[![Main branch build status badge](https://github.com/colindean/keepachangelog-parser-java/actions/workflows/gradle.yml/badge.svg?event=push)](https://github.com/colindean/keepachangelog-parser-java/actions/workflows/gradle.yml)

## History and Inspirations

This parser is heavily inspired by [Vandamme](https://github.com/tech-angels/vandamme), a Ruby implementation of a keepachangelog parser.

## Use it

To use this in your project, include the dependency:

### Gradle

```groovy
dependencies {
    implementation: 'cx.cad.keepachangelog:changelog-parser:<look it up!>'
}
```

Until this is [re-released onto a normal repository](https://github.com/colindean/keepachangelog-parser-java/issues/8),
you'll also need to add this in `settings.gradle`:

```groovy
sourceControl {
    gitRepository("https://github.com/colindean/keepachangelog-parser-java.git") {
        producesModule("cx.cad.keepachangelog:changelog-parser")
    }
}
```

### Maven

Until this is [re-released onto a normal repository](https://github.com/colindean/keepachangelog-parser-java/issues/8),
this uses [JitPack](https://jitpack.io/) for packaging.
Note that while the dependency coordinates are `com.github.colindean:keepachangelog-parser-java`,
the import path is `cx.cad.keepachangelog`.

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.colindean</groupId>
    <artifactId>keepachangelog-parser-java</artifactId>
    <version><!--look it up! --></version>
</dependency>
```
