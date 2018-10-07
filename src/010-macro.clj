;;
;; Macros
;;

;; When evaluated, the expression at the HEAD of a LIST must be a
;; FUNCTION or a MACRO.  (We discuss MACROS now!)

;; If the value of the head is a function, then Clojure evaluates the
;; expressions in the tail of that list and passes those values as
;; arguments to the function whose value is the head.

;; However, sometimes the head of the list is a MACRO instead.

;; The rule for evaluating a list whose head is a MACRO differs from
;; the rule for evaluating a list whose head is a FUNCTION.

;; When the head is a macro, Clojure does not evaluate the expressions
;; in the list's tail.  Instead, it passes those expressions to the
;; macro "unevaluated".  The macro then does whatever it likes with
;; those expressions to produce a new expression that Clojure then
;; evaluates in place of the MACRO form.

;; While a function takes in values and produces another value, a
;; macro takes in expressions to produce another expression, which
;; is then evaluated and so on.

;; Perhaps the simplest function in Clojure is IDENTITY, which just
;; produces the value of its argument.

(identity (+ 1 2 3))                    ;-=> 6

;; Perhaps the simplest macro-like form in Clojure is QUOTE, which
;; just produces the expression which is its argument.  What IDENTITY
;; is to functions, QUOTE is to macros.

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

"The" 'syntax "is just shorthand for" (quote syntax) "."
"The first is translated directly into the second when read."

;; This shorthanding of common usage is fundamental to programming
;; Clojure and other L-word languages.  Most of Clojure's core just
;; consists of names for the most common usage patterns discovered
;; over generations of programmers. All those names are just shorthand
;; for expressions that effectively compile down to only a very few
;; fundamental functions and macros.

;; For example, defining functions is so common in Clojure (and other
;; F-word languages) that Clojure defines a special DEFN macro to make
;; it easier.  For example ...

(defn add1 [n] (+ 1 n))

;; ... is exactly equivalent to ...

(def add1 (fn [n] (+ 1 n)))

;; ... and Clojure immediately translates the former to the latter
;; when compiling.

;; Another common macro is DO.  Remember how LET abstracts out the
;; symbol binding of FN into a syntax convenient for the local naming
;; of values?  DO abstracts out the expression sequencing of FN.  You
;; can think of DO as a LET without bindings or a FN called without
;; arguments.

(do     (println :zero) 2)         ;=> 2
(let [] (println :zero) 2)         ; after printing :zero
((fn [] (println :zero) 2))        ; Same here.

;; DO is useful when you need a side-effect.  2 is the value of all
;; three expressions above.  But they each also print the keyword
;; :zero somewhere when evaluated.  In each case, two expressions are
;; evaluated, then all but the last value is discarded.  The value
;; of (println ...) is always NIL.  (It is designed to be ignored.)

;; Some people find MACROS mysterious, but we've already used several
;; macros without even thinking about them.  Macros take the place of
;; much of the SYNTAX of other languages: they serve the same purpose
;; as declarations, definitions, and conditionals in other languages.

;; Unlike most languages though, Clojure provides DEFMACRO so that
;; programmers can define their own syntax as necessary.  However,
;; all of the syntax you are likely to need for a long while is
;; already defined for you in the clojure.core namespace.

;; For example: IF, AND, and OR are all standard Clojure macros.
;; They have to be macros, because they cannot be functions.

;; Why?  Think about it.

(if true  :then :else)                  ;-=> :then
(if false :then :else)                  ;-=> :else

;; So far so good, but consider the following Clojure definitions.

(def rob {:name "Rob" :dead? true})
(def tom {:name "Tom" :dead? false})

(defn zombie?
  "True if INTRUDER is a zombie."
  [intruder]
  (:dead? intruder))

(zombie? rob)                           ;-=> true
(zombie? tom)                           ;-=> false

(defn greet
  "Greet SOMEONE."
  [someone]
  (let [greeting (str "Hi " (:name someone))]
    (println greeting)
    greeting))

(defn blast
  "Blast SOMETHING to nothing!"
  [something]
  (println ["Blast!" something]))

(defn protect
  "Protect against zombie intruders."
  [intruder]
  (println 'protect)
  (if (zombie? intruder) (blast intruder)
      (greet intruder)))

(protect rob)                          ;-=> nil after blasting in REPL
(protect tom)                          ;-=> "Hello Tom"

;; What would happen if IF were a function called FI?

(defn fi
  "Wrap IF in a silly function."
  [test then else]
  (if test then else))

(defn tcetorp
  "Protection of a kind against intruders."
  [intruder]
  (println 'tcetorp)
  (fi (zombie? intruder) (blast intruder)
      (greet intruder)))

(tcetorp rob)              ; nil again after blasting in REPL
(tcetorp tom)              ; "Hello Tom" after blasting in REPL

;; Unlike our FI, Clojure's IF gets its 3 sub-expressions un-evaluated,
;; and evaluates the first, and only one of the two other expressions,
;; discarding the other unevaluated.

;; After evaluating the first expression (the test), IF evaluates
;; either the second (then) or the third (else) sub-expression
;; depending on whether the test is TRUE (then) or FALSE (else).

;; You can even think of Clojure's literal collection syntax such
;; as :, "", [], #{}, and {} as shorthand for various function
;; expressions such as (keyword ...), (str ...), (vector ...),
;; (set ...), and so on.

;; These strange non-symbol macros are known as 'reader macros' in
;; L-word languages, because they are typically implemented in the
;; 'reader': that mysterious part of the system that turns text into
;; the data expressions of the language: scalars and collections.

;; Like the collection "()[]{} characters, macros are syntax, and are
;; not themselves values.  Clojure will just throw an exception if you
;; attempt to evaluate the name of a macro such as DEF or IF.  (Macro
;; names are not symbols in Clojure.)
