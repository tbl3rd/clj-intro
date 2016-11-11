;;
;; Functions
;;

;; Remember: When evaluated, the value of the expression at the HEAD
;; of a LIST must be a FUNCTION or a MACRO.  (MACROS come later.)
;; Here are examples of functions.

;; FIRST is a function that returns the value at the head of a
;; collection.

;; REST is a function that returns the values in the tail of a
;; collection as a list.

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

;; When evaluated, the head of a list can be any expression whose
;; value is a function.  For example, the head can be a list.

((first (list + - * /)) 1 2 3)          ;-=> 6

((get {:plus + :minus -} :minus) 1 2 3) ;-=> -4

;; A vector is a function of its indexes.

(["Clojure", 0 :to fn?] 2)              ;-=> :to

;; A map is a function of its keys.

(({"add" + "subtract" -} "add") 1 2 3)  ;-=> 6

;; A set is a function of its members.

(#{:Chico :Groucho :Harpo} :Groucho)    ;-=> :Groucho

;; And a keyword is a function of its associations.

(:T {:A :T :T :A :C :G :G :C})          ;-=> :A
(:Harpo #{:Chico :Groucho :Harpo})      ;-=> :Harpo
((:sum {:sum + :difference -}) 1 2 3)   ;-=> 6

;; And, of course, functions are values, just like integers or
;; strings, that can be passed to other functions.
