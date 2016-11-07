;;
;; The power of FN
;;

;; The most important macro in Clojure is FN.  In fact, you can think
;; of the base language as being a bunch of macros defined for you
;; that all compile down to various FN expressions.

;; FN is THAT much fun.

;; A FN expression is a list whose head is FN and whose tail consists
;; of a vector representing parameters, and a sequence of expressions
;; to evaluate in terms of those parameters.  The value of a FN
;; expression is a function that takes an argument for each parameter
;; in the vector and evaluates the expressions on those arguments.

(+ 1 2)                                 ; add 1 to 2
(fn [n] (+ 1 n))                        ; add 1 to N
((fn [n] (+ 1 n)) 2)                    ; n is 2 so return 3
((fn [n] (+ 1 n)) ((fn [n] (+ 1 n)) 2)) ; Add 1 and add 1 again.

;; This gets confusing fast, but let's push it a little further.

((fn [n] (+ 1 n))                       ; the function
 2)                                     ; the 2 bound to n

((fn [n] (+ 1 n))                       ; add 1 to the result ...
 ((fn [n] (+ 1 n))                      ; ... of adding 1 ...
  2))                                   ; ... to 2.

((fn [n] (+ 1 n))                       ; add 1 to the result ...
 ((fn [n] (+ 1 n))                      ; ... of adding 1 to ...
  ((fn [n] (+ 1 n))                     ; ... the result of adding 1 ...
   2)))                                 ; ... to 2.

;; A parameter to a function can name anything.

(fn [f n] (f n))                        ; Apply F to N.

((fn [f n] (f n))                       ; Call "apply F to N" ...
 (fn [n] (+ 1 n))                       ; ... passing it "add 1 to N" ...
 2)                                     ; ... and 2 as arguments.

;; Although in this case, a better name for the F parameter might be ...

((fn [add1 n] (add1 n))                 ; Function to call ADD1 on N.
 (fn [n] (+ 1 n))                       ; Bind (+ 1 n) to ADD1.
 2)                                     ; Bind 2 to N.

;; So now instead of repeating the (fn [n] (+ 1 n)) expression as above,
;; we can just use the name ADD1 to apply it multiple times.

((fn [add1                              ; Introduce symbol ADD1.
      n]                                ; Introduce symbol N.
   (add1                                ; Apply ADD1 to the result of ...
    (add1                               ; applying ADD1 to the result of
     (add1 n))))                        ; applying ADD1 to N.
 (fn [n] (+ 1 n))                       ; ADD1's value is this FN.
 2)                                     ; N's value is this int.

;; Conceptually, FN is how all functions are defined.

;; Perhaps more importantly it is also how all symbols get the values
;; they name, which is the whole point of symbols.

;; But no one would write an entire program this way.
;; What is wrong with it?  (Lost In a Sea of Parentheses?)

((fn [add1                              ; The symbol ADD1 is here.
      n]                                ; The symbol N is here.
   (add1
    (add1
     (add1
      (add1                             ; This part can get long!
       (add1
        (add1
         (add1 n))))))))
 (fn [n] (+ 1 n))                       ; ADD1's value is here.
 2)                                     ; N's value is here.

;; Is there a way to bring a symbol closer to the value it names?
