;;
;; RECUR and LOOP
;;

;; LOOP is just like LET except that it also establishes a "recursion
;; point".

(loop [f (fn [n] (+ 1 n))
       n 0]
  (when (< n 9)
    (println n)
    (f n)))                             ; print 0 and return 1

;; RECUR passes new values to the bindings at a recursion point.
;; The values passed here are F and (f n).  You can think of RECUR as
;; a GOTO that passes new arguments to the bindings in LOOP.

(loop [f (fn [n] (+ 1 n))
       n 0]
  (when (< n 9)
    (println n)
    (recur f                            ; bound to F
           (f n))))                     ; bound to N

;; RECUR always takes the same number of arguments as there are
;; bindings at LOOP.  RECUR re-enters the loop with each of its
;; argument values bound to an expression in the binding vector of the
;; LOOP.  Here the value (comp f f) is bound to F and the value (f n)
;; is bound to N.

(loop [f (fn [n] (+ 1 n))
       n 0]
  (when (< n 99)
    (println n)
    (recur (comp f f)                   ; bound to F
           (f n))))                     ; bound to N

;; A RECUR expression must be in "tail position".  The tail position
;; is the one that determines an expression's value.  RECUR won't
;; compile unless it is in tail position.

;; Here is a simply recursive function.  RECUR allows you to write
;; a simply recursive function that runs without consuming another
;; stack frame on each call.

((fn [f n]
   (loop [f f n n]                      ; bind F to the first argument
     (when (< n 99)                     ; ... and N to the second
       (println n)
       (recur (comp f f)                ; rebound to F
              (f n)))))                 ; rebound to N
 (fn [n] (+ 1 n))                       ; bound to F initially
 0)                                     ; bound to N initially

;; As you see the LOOP expression in that FN form is redundant.  So
;; FN like LOOP also establishes a recursion point.  RECUR effectively
;; just calls the function again passing it new arguments.

((fn [f n]
   (when (< n 99)
     (println n)
     (recur (comp f f)                  ; rebound to F
            (f n))))                    ; rebound to N
 (fn [n] (+ 1 n))                       ; bound to F initially
 0)                                     ; bound to N initially

;; This looks saner with DEFN.

(defn ack [f n]
  (when (< n 99)
    (println n)
    (recur (comp f f)                   ; rebound to F
           (f n))))                     ; rebound to N

(ack (fn [n] (+ 1 n)) 0)

;; The following defines the same function ACK, but consumes more stack.

(defn ack [f n]
  (when (< n 99)
    (println n)
    (ack (comp f f)                     ; Call ACK from ACK.
         (f n))))

(ack (fn [n] (+ 1 n)) 0)

;; Note that an expression can have 0, 1, 2, or more tail positions.

;; (list ...) has none, because LIST needs the value of all its
;; sub-expressions, the elements, to determine its value.

;; (when ...) has 1 tail: its last expression.

;; (if ...) has 2 tails: its THEN and ELSE expressions.

;; (cond ...) has as many tails as it has test expressions, and so on.

;; Similar to RECUR, TRAMPOLINE allows mutually recursive functions
;; that execute without consuming a stack frame on each call.

(defn ping [pong n]
  (when (> n 0)
    (println [:ping n])
    (trampoline pong ping (- n 1))))    ; Goto PONG passing PING ...

(defn pong [ping n]
  (println [:pong n])
  (trampoline ping pong (- n 1)))       ; Goto PING passing PONG ...

(ping pong 9)

;; This style of programming is called continuation-passing.  PONG is
;; PING's continuation and PING is PONG's.  You can think of PING and
;; PONG as a 2-state machine messaging the value of (or event) N.

;; Continuation passing has many applications.  It is fundamental to
;; the Actor model of computation and programming language semantics.
;; You can think of it as a generalization of the usual "direct" call
;; and return style of procedural programming, where the return point
;; is made explicit rather than implied by a stack frame discipline.
