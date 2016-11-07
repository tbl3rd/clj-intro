;;
;; Naming with LET
;;

;; What if we could define a macro that factored out the naming power
;; of FN such that the symbol could be next to the value it names.

;; Clojure has already done that, and called it LET.

(let [add1 (fn [n] (+ 1 n))             ; Bind ADD1 to (FN ...).
      n    2]                           ; Bind N to 2.
  (add1
   (add1
    (add1 n))))                         ; ... and evaluate!

;; LET takes a vector of pairs of expressions, each of which
;; represents a binding of the expression on the left to the
;; value on the right.  That vector is followed by a sequence
;; of expressions that are evaluated using those bindings.

;; The scope of each binding is the list enclosing the LET.

;; Conceptually, LET is still just FN under the hood.

;; LET is a macro that gets the binding vector and the following
;; ADD1 expression and translates it into the following FN expression,
;; which Clojure then evaluates.

((fn [add1 n]                           ; The left sides.
   (add1
    (add1
     (add1
      n))))
 (fn [n] (+ 1 n)) 2)                    ; The right sides.

;; Each binding in the LET vector adds a new parameter to the FN's
;; parameter vector and a new argument to the tail of the FN's
;; enclosing list.

;; LET is just easier to read and write than the FN form because it
;; brings the names and values in the binding together instead of
;; hanging them on opposite ends of the FN's expression, which can get
;; arbitrarily long.

;; But you still can't use the ADD1 symbol outside of the LET form.
;; Every time you want to introduce an ADD1 symbol to an expression,
;; you have to write an enclosing LET form to bind it.

;; Is there a way to define ADD1 such that you could use it anywhere
;; in a program without having to introduce a LET binding for it each
;; time?
