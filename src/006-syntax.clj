;;
;; Summary: Clojure Syntax and Programs
;;

;; And with that, you have virtually the entire syntax of Clojure.

;; There is not much except the primitive scalars (string number
;; keyword character true false nil symbol), the collections (vector
;; map set list), and the rules for evaluating them.

;; And the rules of evaluation are just this.  Strings, numbers,
;; keywords, characters, true, false, and nil all evaluate to
;; themselves.  Vectors, sets, and maps also evaluate to themselves.

;; Their values are printed just as they are read.

;; There is one scalar SYMBOL and one collection LIST that are
;; evaluated differently.

;; Symbols and lists have values that differ from their literal
;; representation in a program.  The result of evaluating them
;; prints differently from how they are read.

;; Symbols name other values.  The value of a symbol is what it names.

;; The value of a list depends on its head, which determines how the
;; tail is evaluated.  The result is always another value.

;; Everything that other languages accomplish through reserved words, 
;; variables, statements, operators, lvalues, rvalues, declarations, 
;; definitions, classes, traits, cases, methods, control structures, 
;; enumerations, types, dictionaries, blocks, tuples, sigils, objects,
;; assignments, statics, fields, members, modules, packages, generics,
;; slices, annotations, overloading, configurations, POMs, tokens,
;; types and serializers -- each with their own syntax and naming
;; conventions -- Clojure does with just the scalars and collections
;; you've already seen.  See the NOTE below.

;; A Clojure program consists of reading a sequence of expressions
;; written as scalars and collections, evaluating them according to
;; the rules above, and printing the results.

;; Writing a Clojure program consists of using symbols to name values,
;; and composing lists of those values such that the side effect of
;; their evaluation achieves the results you want.

;; You are now a Clojure programmer.  While there is a lot more to
;; learn, all you really need now is practice (and vocabulary).

;; NOTE on syntax and macros

;; Furthermore, the fundamental syntax of Clojure consists of just a
;; dozen or so primitive forms.  The rest of the language is built of
;; MACROS on top of that fundamental dozen.  If you understand the
;; fundamentals, you (at least in theory) understand Clojure.

;; The fundamental Clojure forms are: do fn if loop quote var recur
;; with these for Java interop: . catch finally monitor-enter
;;                              monitor-exit new set! throw try

;; I don't know the ClojureScript interop primitives.
