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

"The" 'syntax "is just shorthand for" (quote syntax) "."
"The first is translated directly into the second when read."

;; This short-handing of common usage is fundamental to programming
;; Clojure and other L-word languages.  Most of Clojure's core just
;; consists of names for the most common usage patterns discovered
;; over generations. All those names are just shorthand for
;; expressions that effectively compile down to only a very few
;; fundamental functions and macros.

;; In fact, you can think of :, "", [], #{}, and {} as shorthand for
;; various function expressions such as (keyword ...), (str ...),
;; (vector ...), (set ...), and so on.

;; These strange non-symbol macros are known as 'reader macros' in
;; L-word languages, because they are typically implemented in the
;; 'reader': the part of the system that turns text into the data
;; expressions of the language: scalars and collections.

;; Some people find MACROS mysterious, but we've already used several
;; macros without even thinking about them.  Macros take the place of
;; much of the SYNTAX of other languages: they serve the same purpose
;; as declarations, definitions, and conditionals in other languages.

;; Unlike other languages though, Clojure provides DEFMACRO so that
;; programmers can define their own syntax as necessary.  However,
;; all of the syntax you are likely to need for a long while is
;; already defined for you.

;; For example: IF, AND, and OR are all standard Clojure macros.
;; They have to be macros, because they cannot be functions.
;; Why?  Think about it.

(if true  :then :else)                  ;-=> :then
(if false :then :else)                  ;-=> :else

;; Consider the following Clojure functions.

(def rob {:name "Rob" :dead? true})
(def tom {:name "Tom" :dead? false})

(defn zombie?
  "True iff INTRUDER is a zombie."
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
  "Wrap if in a silly function."
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
