;;
;; FN makes functions
;;

;; A FN expression is a list whose head is FN and whose tail consists
;; of a vector representing parameters, and a sequence of expressions
;; to evaluate in terms of those parameters.

(fn [two parameters] (str two parameters))

;; Calling a function substitutes the values of arguments for the
;; symbols in the function's expression.  You call a function by
;; placing it at the head of a list whose tail is its arguments.

((fn [x y] (str x y)) 2 :arguments)     ;-=> "2:arguments"

;; We say: X is bound to 2 and, Y is bound to :arguments in the
;; expression (str x y).

;; The value of a FN expression is a function that takes an argument
;; for each parameter in the vector and evaluates the expressions on
;; those arguments.

(+ 1 2)                                 ; add 1 to 2
(fn [n] (+ 1 n))                        ; add 1 to N
((fn [n] (+ 1 n)) 2)                    ; n is 2 so return 3
((fn [n] (+ 1 n)) ((fn [n] (+ 1 n)) 2)) ; Add 1 and add 1 again.

;; This gets confusing fast, but let's push it a little further.

((fn [n] (+ 1 n))                       ; the function
 2)                                     ; the 2 bound to n

((fn [n] (+ 1 n))                       ; add 1 to the result
 ((fn [n] (+ 1 n))                      ; ... of adding 1
  2))                                   ; ... to 2.

((fn [n] (+ 1 n))                       ; Add 1 to the result
 ((fn [n] (+ 1 n))                      ; ... of adding 1 to
  ((fn [n] (+ 1 n))                     ; ... the result of adding 1
   2)))                                 ; ... to 2.

;; A parameter to a function can name anything -- even another
;; function.

(fn [f n] (f n))                        ; a function to apply F to N

((fn [f n] (f n))                       ; Call "apply F to N"
 (fn [n] (+ 1 n))                       ; ... passing it "add 1 to N"
 2)                                     ; ... and 2 as arguments.

;; Although in this case, a better name for the F parameter is ADD1.

((fn [add1 n] (add1 n))                 ; Function to call ADD1 on N.
 (fn [n] (+ 1 n))                       ; Bind (fn [n] (+ 1 n)) to ADD1.
 2)                                     ; Bind 2 to N.

;; So now instead of repeating the (fn [n] (+ 1 n)) expression as above,
;; we can just use the name ADD1 to apply it multiple times.

((fn [add1                              ; Introduce symbol ADD1.
      n]                                ; Introduce symbol N.
   (add1                                ; Apply ADD1 to the result of ...
    (add1                               ; applying ADD1 to the result of
     (add1 n))))                        ; ... applying ADD1 to N.
 (fn [n] (+ 1 n))                       ; ADD1's value is this FN.
 2)                                     ; N's value is this int.

;; Conceptually, FN is how every function is defined.

;; Perhaps more importantly it is also how all symbols are introduced
;; (as parameters) and get the values they name (as arguments).  And
;; remember that naming things is the whole point of symbols.

;; But no one would write an entire program this way.

((fn [add1                    ; Introduce the symbol ADD1 way up here.
      n]                      ; Introduce the symbol N way up here.
   (add1
    (add1
     (add1
      (add1
       (add1                            ; And this part CAN get long!
        (add1
         (add1
          (add1
           (add1
            (add1 n)))))))))))   ; Oh my!  Clutch my pearls!
 (fn [n] (+ 1 n))                ; Specify ADD1's value way down here.
 2)                              ; Specify N's value way down here.

;; What is wrong with it?  (Lost In a Sea of Parentheses?)

;; How can we bring a symbol closer to the value it names?
