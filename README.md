# keepachangelog parser

This library implements a simple parser for [keepachangelog](https://keepachangelog.com)-formatted `CHANGELOG.md` files. Changelogs tracked in this human-readable format become machine-parseable and thus much more usable for fun things like analysis and involving the changelog in the release build process.


## History and Inspirations

This parser is heavily inspired by [Vandamme](https://github.com/tech-angels/vandamme), a Ruby implementation of a keepachangelog parser.

## Use it

To us this in your project, include the dependency:

Gradle:

```groovy
dependencies {
    compile group: 'cx.cad.keepachangelog', name: 'changelog-parser', version: '<look it up!>'
}
```

Maven:

```xml
<dependency>
    <groupId>cx.cad.keepachangelog</groupId>
    <artifactId>changelog-parser</artifactId>
    <version><!--look it up! --></version>
</dependency>
```

