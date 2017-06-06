# keepachangelog parser

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## 0.1.0

### Added

* Support for "Unreleased" entries
* Support for "[YANKED]" entries
* License file is now in shipped Jar
* Set some Jar manifest attributes
* Significantly more tests to support new features, cleaned up tests for easier
  management of code.
* Documentation

### Changed

* Moved extraction to an internal package

## 0.0.5

### Added

* Initial usable release.
* Parsing any CHANGELOG.md conforming strictly to the standard but omits an 
  `Unreleased` entry and does not use the `Yanked` tag should work.  


<!-- TEMPLATE

## Major.Minor.Patch - YYYY-MM-DD

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

-->
