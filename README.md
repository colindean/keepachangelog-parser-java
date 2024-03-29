# keepachangelog parser

This library implements a simple parser for [keepachangelog](https://keepachangelog.com)-formatted `CHANGELOG.md` files. Changelogs tracked in this human-readable format become machine-parseable and thus much more usable for fun things like analysis and involving the changelog in the release build process.

[ ![Download](https://api.bintray.com/packages/colindean/cx.cad.keepachangelog/changelog-parser/images/download.svg) ](https://bintray.com/colindean/cx.cad.keepachangelog/changelog-parser/_latestVersion)
[![Javadocs](https://javadoc.io/badge/cx.cad.keepachangelog/changelog-parser.svg)](https://javadoc.io/doc/cx.cad.keepachangelog/changelog-parser)
[![Build Status](https://travis-ci.org/colindean/keepachangelog-parser-java.svg?branch=master)](https://travis-ci.org/colindean/keepachangelog-parser-java)

## History and Inspirations

This parser is heavily inspired by [Vandamme](https://github.com/tech-angels/vandamme), a Ruby implementation of a keepachangelog parser.

## Use it

To us this in your project, include the dependency:

Gradle:

```groovy
repositories {
  jcenter()
}
dependencies {
    compile group: 'cx.cad.keepachangelog', name: 'changelog-parser', version: '<look it up!>'
}
```

Maven:

```xml
<!-- Ensure that JCenter is already added as a repository. -->
<dependency>
    <groupId>cx.cad.keepachangelog</groupId>
    <artifactId>changelog-parser</artifactId>
    <version><!--look it up! --></version>
</dependency>
```
