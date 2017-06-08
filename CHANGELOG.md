# keepachangelog parser

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## 0.1.2 - 2017-06-08

### Added

* Signed releases

## 0.1.1 - 2017-06-06

This release resolved the deployment problem from 0.1.0. It contains no material
changes beyond that.

## 0.1.0 - 2017-06-06 [YANKED]

This release was yanked because of a deployment problem.

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

## 0.0.5 - 2017-06-05

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
