;;
;; Naming with DEF
;;

;; What if there was a way to define a symbol as in LET such that the
;; it could be used anywhere in a program.  Call it DEF for 'define'.

(def add1 (fn [n] (+ 1 n)))             ;-=> #'boot.user/add1
(add1 2)                                ;-=> 3

;; Again, DEF is special like LET.  It binds a symbol, such as

add1                                    ;-=> #function[boot.user/add1]

;; ... to a value, such as the value of

(fn [n] (+ 1 n))

;; ... and makes that symbol available outside the (DEF ...) form.

;; You can think of DEF as adding bindings to a global implicit LET.
;; That global implicit LET in turn is doing its usual thing to a
;; global implicit FN, and so on.  That won't often lead you astray.

;; Since so many symbols are defined to be functions, there is another
;; macro built on top of DEF called DEFN for 'define function'.

(defn add1 [n] (+ 1 n))                 ;-=> #'boot.user/add1

;; When evaluated, that DEFN expression translates exactly into the
;; DEF expression above, which translates into a LET expression, which
;; translates into a FN expression, which ... You get the picture.

;; Using DEF and LET to bind symbols to values is how you name most
;; things in Clojure.

(def twenty-three 23)                   ;-=> #'boot.user/twenty-three
twenty-three                            ;-=> 23

(def odds (filter odd? (range)))        ;-=> #'boot.user/odds
(def two-odds (take 2 odds))            ;-=> #'boot.user/two-odds
two-odds                                ;-=> (1 3)

(def lojure (vec (rest "Clojure")))     ;-=> #'boot.user/lojure
lojure                                  ;-=> [\l \o \j \u \r \e]

(let [lojure (rest "Clojure")
      l (first lojure)
      ure (drop 3 lojure)]
  (def lure (apply str l ure)))         ;-=> #'boot.user/lure
lure                                    ;-=> "lure"

;; Note that the LOJURE, L, and URE symbols are all bound local to
;; their (LET ...) form, whereas the LURE bound in (DEF ...) escapes
;; the (LET ...) form into ... something outside the (LET ...).

;; The place where DEF symbols are bound is called a NAMESPACE.
