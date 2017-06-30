# flij

A toy interpreter for a toy LISP dialect I'm making up as I go along.

## Instructions

Run ``build-and-run-repl.sh`` to do just that.

## Defined symbols

### [Built-ins](src/main/java/com/github/fauu/flij/builtin/)

``
put, putln, def, fn, quote, eval, apply, =, <, <=, >, >=, cond, if, and, or, \+, \-, \*, \/, sqrt, prepend, head, tail, len, subseq, reverse
``

### [Standard library](src/main/resources/lib/std.flj)

``
nil, inc, dec, sqr, fact, not, empty?, map, join-reversed, join
``

## Demo

```lisp
;; Comments are denoted by two semicolons

;; <expression> ;; => <evaluation>

;; Expression is either an Atom (Symbol, Number, Character, String, Boolean) or a List.

;; Numbers
1 ;; => 1.0
1.23 ;; => 1.23
-10 ;; => -10.0

;; Characters
\ą ;; => \ą

;; Strings
"Hello world" ;; => "Hello world"

;; Symbols
a ;; => a

;; Lists
;; Every expression is evaluated by default.
;; With a list, the first element is treated as a symbol for built-in or a function.
;; The rest of the elements is treated as parameters to that function:
(1 2 3) ;; => EVAL ERROR: Exprected symbol at list start
(+ 1 2 3) ;; => 6.0

;; Built-ins are special forms implemented inside the interpreter.
;; To stop default list evaluation, 'quote' built-in can be used:
(quote (1 2 3)) ;; => (1.0 2.0 3.0)
;; There is also a convinient shorthand:
'(1 2 3) ;; => (1.0 2.0 3.0)
(= (quote (1 2 3)) '(1 2 3)) ;; => true

;; Basic arithmetic
(+ 2 2) ;; => 4.0
(- 2 3) ;; => -1.0
(* 2 4) ;; => 8.0
(/ 2 5) ;; => 0.4

;; A definition can be assigned to a symbol using 'def' built-in:
(def x 3) ;; => nil
x ;; => 3.0

;; 'fn' built-in is used to create an anonymous function:
(fn (n) (- n 1)) ;; => <1-ary function>
;; which can be applied:
(apply (fn (n) (- n 1)) 10) ;; => 9.0
;; or assigned to a symbol:
(def decrement (fn (n) (- n 1))) ;; => nil
;; and then applied using the symbol:
(decrement 10) ;; => 9.0

;; 'if' built-in is used to create conditional expressions:
(if (> 9001 9000) "over" "under") ;; => "over"

;; Let's define a recursive factorial function, using previously defined 'decrement' function:
(def factorial
  (fn (n)
    (if (= n 0)
      1
      (* n (fact (dec n)))))) ;; => nil
(factorial 3) ;; => 6.0

;; nil is equivalent to an empty sequence
(= nil '()) ;; => true

;; Lists and strings are both sequences:
(= nil "") ;; => true
(prepend \f "lij") ;; => "flij"
(prepend 1 '(3 3 7)) ;; => (1.0 3.0 3.0 7.0)
(head "Piotr") ;; => \P
(head '(1 2 3)) ;; => 1.0
(tail "emacs") ;; => "macs"
(len "konstantynopolitańczykowianeczka") ;; => 32.0
(subseq "meme" 2 4) ;; => "me"
(reverse "god") ;; => "dog"

;; Let's define a map higher-order function that applies a given function to all elements of a sequence:
(def map
  (fn (function sequence)
    (if (empty? sequence)
      '()
      (prepend
        (apply function (head sequence))
        (map function (tail sequence)))))) ;; => nil
(map (fn (n) (+ n 100)) '(1 2 3)) ;; => (101.0 102.0 103.0)
(map 'reverse '("er" "rev" "es")) ;; => ("re" "ver" "se")
```

