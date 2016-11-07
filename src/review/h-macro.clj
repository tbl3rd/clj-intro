;;
;; Macros
;;

;; When evaluated, the expression at the HEAD of a LIST must be a
;; FUNCTION or a MACRO.  (We discuss MACROS now!)

;; Remember: The head of a list determines how Clojure evaluates it.

;; If the value of the head is a function, then Clojure evaluates the
;; expressions in the tail of that list and passes those values as
;; arguments to the function whose value is the head.

;; Sometimes the head of the list is a MACRO instead of a function.
;; The rule for evaluating a list whose head is a MACRO differs from
;; the rule for evaluating a list whose head is a FUNCTION.

;; When the head is a macro, Clojure does not evaluate the expressions
;; in the list's tail.  Instead, it passes those expressions to the
;; macro "unevaluated".  The macro then does whatever it likes with
;; those expressions to produce a new expression that Clojure then
;; evaluates in place of the MACRO.

;; While a function takes in values and produces another value, a
;; macro takes in expressions to produce another expression, which
;; is then evaluated and so on.

;; Perhaps the simplest function in Clojure is IDENTITY, which just
;; produces the value of its argument.

(identity (+ 1 2 3))                    ;-=> 6

;; Perhaps the simplest macro in Clojure is QUOTE, which just produces
;; the expression which is its argument.  What IDENTITY is to functions,
;; QUOTE is to macros.

(quote (+ 1 2 3))                       ;-=> (+ 1 2 3)

(quote    (identity (+ 1 2 3)))         ;-=> (identity (+ 1 2 3))
(identity (quote    (+ 1 2 3)))         ;-=> (+ 1 2 3)
(quote    quote)                        ;-=> quote
(quote    identity)                     ;-=> identity
(identity identity)                     ;-=> #function[clojure.core/identity]

;; Unlike IDENTITY, QUOTE is so useful that it gets its own syntax.

'identity                               ;-=> identity
'quote                                  ;-=> quote
'()                                     ;-=> ()
'(0 1 2 3 4)                            ;-=> (0 1 2 3 4)

;; Some people find MACROS mysterious, because most languages do not
;; let programmers define their own.  But we've already used several
;; macros without even thinking about them.  Macros take the place of
;; the SYNTAX of other languages: they serve the same purpose as
;; declarations, definitions, and conditionals in other languages.

;; IF, AND, and OR are all standard Clojure macros.  They have to be.
;; Why?  Think about it.

(if true  :then :else)                  ;-=> :then
(if false :then :else)                  ;-=> :else

;; When you understand literals, functions, and macros, you understand
;; the language.  There is nothing else except the vocabulary of
;; literals, functions, and macros provided by the standard library,
;; and how to combine them into what you need to solve your problem.
