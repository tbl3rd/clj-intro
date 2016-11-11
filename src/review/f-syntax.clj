;;
;; Summary: Clojure Syntax and Programs
;;

;; And with that, you have virtually the entire syntax of Clojure.

;; There is not much else except the primitive atoms (string number
;; keyword character true false nil symbol), the collections (vector
;; map set list), and the rules for evaluating them.

;; And the rules of evaluation are just this.  Strings, numbers,
;; keywords, characters, true, false, and nil all evaluate to
;; themselves.  Their values are printed just as they are read.

;; There is one atom SYMBOL and one collection LIST that are
;; evaluated differently.

;; Symbols and lists have values that differ from their literal
;; representation in a program.  The result of evaluating them
;; prints differently from how they are read.

;; Symbols name other values.  The value of a symbol is what it names.

;; The value of a list depends on its head, which determines how the
;; tail is evaluated.  The result is always another value.

;; Everything that other languages accomplish through reserved words,
;; variables, statements, operators, lvalues, rvalues, declarations,
;; definitions, classes, methods, enumerations, types, dictionaries,
;; blocks, objects, assignments, statics, fields, members, modules,
;; packages, generics, annotations, overloading, configurations, POMs,
;; tokens, and serializers -- each with their own syntax and naming
;; conventions -- Clojure does with just the atoms and collections
;; you've already seen.

;; A Clojure program consists of reading a sequence of expressions
;; written as atoms and collections, evaluating them according to the
;; rules above, and printing the results.

;; Writing a Clojure program consists of using symbols to name values,
;; and composing lists of those values such that the side effect of
;; their evaluation achieves the results you want.

;; You are now a Clojure programmer.  While there is a lot more to
;; learn, all you really need is practice.
