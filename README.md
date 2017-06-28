# flij

An interpreter for a LISP dialect I'm making up as I go along. Work very much in progress.

## Instructions

Run ``build-and-run-repl.sh`` to do just that.

## Defined symbols

### [Built-ins](src/main/java/com/github/fauu/flij/builtin/)

* put, putln
* def
* fn
* quote
* eval
* =
* <, <=, >, >=
* cond, if
* and, or
* \+, \-, \*, \/
* sqrt
* len
* subseq
* reverse

### [Standard library](src/main/resources/lib/std.flj)

* inc
* dec
* sqr
* fact
* not
