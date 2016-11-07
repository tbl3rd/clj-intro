;;
;; Naming with DEF
;;

;; What if there was a way to define a symbol as in LET such that the
;; it could be used anywhere in a program.  Call it DEF for 'define'.

(def add1 (fn [n] (+ 1 n)))             ;-=> #'boot.user/add1
(add1 2)                                ;-=> 3

;; Again, DEF is a macro like LET.  It binds a symbol, such as

add1                                    ;-=> #function[boot.user/add1]

;; ... to a value, such as

(fn [n] (+ 1 n))

;; You can think of DEF as putting bindings in a global implicit LET.
;; That global implicit LET in turn is doing its usual thing to a
;; global implicit FN, and so on.

;; As often in Clojure, under the hood it is FNs all the way down.

;; Since so many symbols are defined to be functions, there is another
;; macro built on top of DEF called DEFN for 'define function'.

(defn add1 [n] (+ 1 n))                 ;-=> #'boot.user/add1

;; When evaluated, that DEFN expression translates exactly into the
;; DEF expression above, which translates into a LET expression, which
;; translates into a FN expression, which ... You get the picture.

;; DEF and LET binding symbols to values is how you name nearly
;; everything in Clojure.
