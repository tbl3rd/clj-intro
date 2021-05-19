;;
;; Functions
;;

;; When evaluating a list, the value of the expression at the HEAD
;; of the list is most often a FUNCTION.

;; FIRST and REST are examples of functions.

;; FIRST returns the value at the head of a collection.

;; REST returns the values in the tail of a collection as a list.

;; Notice that we say "FIRST and REST are functions" when we really
;; should say that "FIRST and REST are symbols whose values are
;; functions" or that "'first' and 'rest' name two functions".

;; It can be confusing at first, but it is really no different from
;; saying "Rover and Spot are my dogs" instead of saying "'Rover' and
;; 'Spot' are the names of my dogs".

(first [:head :tail :tail])             ;-=> :head
(rest  [:head :tail :tail])             ;-=> (:tail :tail)
(first (list :head :tail))              ;-=> :head
(rest  (list :head :tail))              ;-=> (:tail)
(first [:head])                         ;-=> :head
(rest  [:head])                         ;-=> ()
(first [])                              ;-=> nil
(rest  [])                              ;-=> ()

;; The value of a (rest ...) expression is always a list even if there
;; is nothing in it.  The head of an empty collection is nil.

;; The 'operators' of other languages are also just Clojure functions.
;; For example, these are all functions too.

+ - * / = == > < <= >=

;; That means they have values just like 23 or "string"!
;; Which means you can put them in a list and count them.

(count (list + - * / = == > < <= >=))   ;-=> 10

;; When evaluated, the head of a list can be any expression whose
;; value is a function.  For example, the head can be a list.

((first (list + - * /)) 1 2 3)          ;-=> 6

;; GET is a function which gets the value in the collection (its first
;; argument) associated with the value in its second argument.

((get {:plus + :minus -} :minus) 1 2 3) ;-=> -4

;; But unlike in other languages, Clojure collections can also
;; function on their own.

;; For example, a vector is a function of its indexes.

(get ["Clojure", 0 :to fn?] 2)          ;-=> :to
(    ["Clojure", 0 :to fn?] 2)          ;-=> :to

;; A map is a function of its keys.

(({"add" + "subtract" -} "add") 1 2 3)  ;-=> 6

;; A set is a function of its members.

(#{:Chico :Groucho :Harpo} :Groucho)    ;-=> :Groucho

;; And a keyword is a function of its associations.

(:T {:A :T :T :A :C :G :G :C})          ;-=> :A
(:Harpo #{:Chico :Groucho :Harpo})      ;-=> :Harpo
((:sum {:sum + :difference -}) 1 2 3)   ;-=> 6

;; And finally, because functions are values, just like integers or
;; strings, they can be passed to other functions as arguments and
;; returned as results.

(map (comp keyword str) (range 4))      ;-=> (:0 :1 :2 :3)

;; COMP takes the functions KEYWORD and STR and returns a new function
;; that is a composition of its arguments.
;;
;; ((COMP F G) X) is exactly equivalent to (F (G X)), but COMP lets
;; you talk about the composition of F and G without worrying about
;; the parameters F and G take, such as X.  This is sometimes called
;; "point-free" notation in F-word languages.

;; MAP takes the function that is the value of its first argument,
;; applies that function to the sequences of values that are the
;; values of the REST of its arguments.  RANGE just returns the
;; sequence of non-negative integers up to its argument 4.

;; That is how you program in Clojure.
