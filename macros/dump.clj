;;
;; a debugging macro to dump the values of expressions
;;

;; We want something to use when debugging such that the content of
;; this comment ...

(def beatle? #{:george :john :paul :ringo})

(comment
  (dump beatle?)
  (dump (count beatle?))
  (dump (beatle? :ringo))
  (dump (beatle? :mick)))

;; ... prints the lines in this comment.

(comment
  [beatle? #{:george :john :paul :ringo}]
  [(count beatle?) 4]
  [(beatle? :ringo) true]
  [(beatle? :mick) false])

;; DUMP cannot be defined as a function.  Why?

;; DUMP can be defined as a macro that turns this ...

(comment
  (dump beatle?)
  (dump (count beatle?))
  (dump (beatle? :ringo))
  (dump (beatle? :mick)))

;; ... into this.

(comment
  (println [(quote beatle?) beatle?])
  (println [(quote (count beatle?)) (count beatle?)])
  (println [(quote (beatle? :ringo)) (beatle? :ringo)])
  (println [(quote (beatle? :mick)) (beatle? :mick)])
  )

;; What if we had a function that produced the list we want from the
;; list we have?

(defn dump-fn
  [expression]
  (list 'println [(list 'quote expression) expression]))

(dump-fn (quote beatle?))     ;-=> (println [(quote beatle?) beatle?])
(dump-fn (quote (count beatle?)))

;; Given DUMP-FN we can define a macro that transforms the expression
;; we have into the expression we want.

(defmacro dump
  [expression]
  (dump-fn expression))

;; The DUMP macro just passes its EXPRESSION unevaluated to DUMP-FN,
;; which then turns the incoming expression into the PRINTLN form,
;; which Clojure then evaluates.

(dump beatle?)
(dump (count beatle?))

;; The MACROEXPAND function takes a quoted expression and returns the
;; resulting expression that Clojure evaluates after expanding macros.

(macroexpand '(dump beatle?))
;;-=> (clojure.core/println [(quote beatle?) beatle?])

(macroexpand '(dump (count beatle?)))
;;-=> (clojure.core/println [(quote (count beatle?)) (count beatle?)])

;; The construction of new list forms from old list forms is such a
;; common thing when writing macros, that Clojure introduces some more
;; shorthand quoting forms: the Quasiquote ` (backtick) and Unquote ~
;; (tilda).  You can use them to construct a list template that the
;; macro fills in.

;; Quasiquote is like quote except that inside a Quasiquoted form, an
;; Unquoted symbol is replaced with the expression bound to it.

(let [expression 'beatle?]
  `(println [(quote ~expression) ~expression]))
;;-=> (clojure.core/println [(quote beatle?) beatle?])

;; You can think of Quasiquote as introducing a template into which
;; new expressions can be injected with Unquote.

(defmacro dump
  [expression]
  `(println [(quote ~expression) ~expression]))

;; Unlike QUOTE, you don't actually need Quasiquote and Unquote to
;; write macros, but you will see them used by others, and they can
;; streamline macros by removing the need for helper functions like
;; DUMP-FN.

(dump beatle?)
(dump (beatle? :ringo))
(dump (if (beatle? :ringo) "Found!" "Nope."))
(dump (if (beatle? :mick) "Found!" "Nope."))

;; There is at least one obvious improvement we can make to DUMP.

;; What is the value of (dump ...)?  It is always NIL.

;; But we want to use this for debugging.

;; Final DUMP macro.  Explain.

(defmacro dump
  [expression]
  `(let [x# ~expression]
     (do
       (println ['~expression x#])
       x#)))

(defmacro dump
  "Dump a vector [FORM VALUE] where FORM is the EXPRESSION source
  and VALUE is its value."
  [expression]
  `(let [x# ~expression]
     (do
       (println ['~expression x#])
       x#)))
