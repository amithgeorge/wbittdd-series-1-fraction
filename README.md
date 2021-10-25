# amithgeorge/fractions

This an exercise for Series 1 of the course [The World’s Best Intro to TDD: Level 1](https://www.jbrains.ca/training/course/worlds-best-intro-to-tdd/)

## Dev Setup

1. Install latest LTS release of [Java JDK](https://www.azul.com/downloads/?package=jdk).
2. Install latest release of [Clojure tools](https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools).
3. Install latest release of [Babashka](https://github.com/babashka/babashka#installation)

## Tests

```shell
bb run unit-tests
```

## Build jar

```shell
bb run uberjar
```

## Installation

## Usage

## Options

## Examples

```clojure
;; adding whole numbers
(is (= -5 (sut/add -7 2)))

;; adding fractions
(is (= (sut/->fraction -2 5)
       (sut/add (sut/->fraction -3 5)
                (sut/->fraction 1 5))))

;; adding fraction and whole number
(is (= (sut/->fraction 3 2)
       (sut/add 1 (sut/->fraction 1 2))))

;; fraction answer is in reduced form
(is (= (sut/->fraction 1 2)
       (sut/add (sut/->fraction 3 4)
                (sut/->fraction -1 4))))

;; fractions with negative denominators
(is (= (sut/->fraction -1 -2)
       (sut/add (sut/->fraction 1 -4)
                (sut/->fraction -3 -4))))

;; answer is whole number if answer's denominator is 1 or -1
(is (= 1
       (sut/add (sut/->fraction 1 3)
                (sut/->fraction 2 3))))

```

### Bugs

### Any Other Sections

### That You Think

### Might be Useful
