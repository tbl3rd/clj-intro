;;
;; Every valid Clojure EXPRESSION has a VALUE.
;;

;; The value of many Clojure expressions look just like the
;; expression.  That is true of all scalars except the SYMBOL.
;;
;; It is also true of all collections except the LIST.
;;
;; What's going to happen when Clojure evaluates a (LIST)?

;; Something wonderful ...

()                                      ; is a list
(list)                                  ;-=> ()
(vector)                                ;-=> []
(set (list :a :set))                    ;-=> #{:a :set}
(+ 0 1 2 3 4 5 (* 2 6) 7 8)             ;-=> 42
(fn? list)                              ;-=> true
(list? (list))                          ;-=> true
(fn? (list))                            ;-=> false
(list? fn?)                             ;-=> false
(list (list (list (list ()))))          ;-=> ((((()))))
(list () () () ())                      ;-=> (() () () ())
(str "Answer" \space \i \s \space 42)   ;-=> "Answer is 42"
,,,,,,,(vector (name :Answer) :is 42)   ;-=> ["Answer" :is 42]
(count (vector (name :Answer) :is 42))  ;-=> 3
(odd? 1)                                ;-=> true
(every? even? [2 4 6 8 10 12 14 16])    ;-=> true
(first ["Clojure", 0 :to fn?])          ;-=> "Clojure"
(get   ["Clojure", 0 :to fn?] 2)        ;-=> :to
(get "Clojure" 3)                       ;-=> \j
(name :keyword)                         ;-=> "keyword"
(keyword "name")                        ;-=> :name
(if (even? 2) :even :odd)               ;-=> :even
(str "I can't" \space (name (if (odd? 2) :odd :even)) \.)
(and (every? odd? [7 23]) (every? even? [2 42]))
(or  (first (list :first :rest)) (rest (list :first :rest)))
(and (first (list :first :rest)) (rest (list :first :rest)))

;; The evaluation of a list expression depends on the first expression
;; in the list.  A list's FIRST expression is often called its HEAD.
;; The REST of the list is occasionally called its TAIL.

;; As in life, a list's HEAD determines how its TAIL is evaluated.

;; Usually, the HEAD is a SYMBOL or another LIST whose value is a
;; function.  Occasionally the HEAD is a symbol that names a MACRO.
;; In either case, the HEAD determines how the TAIL is evaluated.

;; For example, LIST is a symbol whose value is a function, and that
;; function constructs a list of its TAIL values.

list                     ;-=> #function[clojure.lang.PersistentList/1]
(list 0 1 2)             ;-=> (0 1 2)
(list list)              ;-=> (#function[clojure.lang.PersistentList/1])
(list fn?)               ;-=> (#function[clojure.core/fn?])
((first (list fn? + - count)) list)     ;-=> true
((first (list list fn? +)) 0 1 2)       ;-=> (0 1 2)

;; Learn to count!  Then use COUNT to check your counting.
