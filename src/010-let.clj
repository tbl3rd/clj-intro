;;
;; Naming with LET
;;

;; What if we could define a macro that factored out the naming power
;; of FN such that the symbol could be next to the value it names.

;; Clojure has already done that, and called it LET.  LET factors out
;; the naming capability of FN with a simpler syntax.

(let [add1 (fn [n] (+ 1 n))             ; Bind ADD1 to (FN ...).
      n    2]                           ; Bind N to 2.
  (add1
   (add1
    (add1
     (add1
      (add1 n))))))                     ; ... and evaluate!

;; LET takes a vector of pairs of expressions, each pair of which
;; represents a binding of the expression on the left to the value on
;; the right.  That "binding vector" is followed by a sequence of
;; expressions that are evaluated using those bindings.

;; The scope of each binding is the list enclosing the LET.

;; Conceptually, LET is still just FN under the hood.

;; So LET is a macro that gets the binding vector and the following
;; ADD1 expression and translates it into the following FN expression,
;; which Clojure then evaluates.

((fn [add1 n]                           ; The left sides.
   (add1
    (add1
     (add1
      (add1
       (add1
        n))))))
 (fn [n] (+ 1 n)) 2)                    ; The right sides.

;; Each binding in the LET vector adds a new parameter to a FN's
;; parameter vector and a new argument to the tail of that FN's
;; enclosing list.

;; LET is easier to read and write than the FN form because it
;; brings the names and values in the binding together instead of
;; hanging them on opposite ends of the FN's expression, which can get
;; arbitrarily long.

;; Symbols introduced by (LET ...) are bound only within its ()s.
;; This makes LET handy for naming values for local use within other
;; expressions.  The binding of symbols in a LET form is exactly like
;; the binding of values to parameters in a function call because they
;; actually are the same.

;; Symbols bound by LET differ from those bound by DEF because DEF's
;; symbols are bound in a namespace external to the (DEF ...) form,
;; whereas LET's symbols are bound only within the (LET ...) form
;; itself and are invisible (or undefined) outside of the LET form.

;; When LET forms are nested, symbols in outer LETs are bound in the
;; inner LETs.  Language lawyers call that "lexical scope" or "block
;; structure".  The scope of a DEF bound symbol extends from the
;; occurance of that symbol through the end of its namespace.
