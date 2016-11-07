;;
;; Sequences
;;

;; Remember REST?  It returns a collection without its head as a list.

;; But does this make sense to you?

(rest  "Clojure")                       ;-=> (\l \o \j \u \r \e)
(rest  [0 1 2 3 4])                     ;-=> (1 2 3 4)
(rest #{0 1 2 3 4})                     ;-=> (1 4 3 2)
(rest  {:a 0 :b 1 :c 2})                ;-=> ([:b 1] [:c 2])

(rest  "Clojure")                       ; Why not  "lojure"        ?
(rest  [0 1 2 3 4])                     ; Why not  [1 2 3 4]       ?
(rest #{0 1 2 3 4})                     ; Why not #{1 4 3 2}       ?
(rest  {:a 0 :b 1 :c 2})                ; Why not  {[:b 1] [:c 2]} ?

;; REST actually produces a SEQUENCE.  A SEQUENCE prints as a list.

;; SEQ turns a collection into a sequence if it isn't already.

(seq "Clojure")                         ;-=> (\C \l \o \j \u \r \e)
(seq (seq "Clojure"))                   ;-=> (\C \l \o \j \u \r \e)
(seq (seq (seq "Clojure")))             ;-=> (\C \l \o \j \u \r \e)

;; SEQ turns a string into a sequence of its characters.
;; SEQ turns a vector into a sequence of its elements.
;; SEQ turns a set    into a sequence of its members.
;; SEQ turns a map    into a sequence of its [key value] entries.

;; Many functions produce and consume sequences, which is why they can
;; operate on any collection.  The function first calls SEQ.

;; One function that SEQ makes possible is RANGE.
;; RANGE returns a sequence of integers.

(range 10)                              ;-=> (0 1 2 3 4 5 6 7 8 9)
(range 3 8)                             ;-=> (3 4 5 6 7)
(range 0 10 3)                          ;-=> (0 3 6 9)
(def whole (range))                     ;-=> #'boot.user/whole

;; What is WHOLE?  WHOLE is the positive integers starting at 0.

(take 10 whole)                         ;-=> (0 1 2 3 4 5 6 7 8 9)
(take 3 (drop 99999 whole))             ;-=> (99999 100000 100001)

;; Sequences are lazy lists that can be arbitrarily long, even
;; infinite.  You get only what you take.  A large collections or a
;; long sequence becomes just another value in a program.

;; INTO is one convenient way to turn a finite SEQ back into a
;; collection of whatever type you need.

(into  [] (rest  [0 1 2 3 4]))          ;-=>  [1 2 3 4]
(into ${} (rest #{0 1 2 3 4}))          ;-=> #{1 2 3 4}
(into  {} (rest  {:a 0 :b 1 :c 2}))     ;-=>  {:b 1, :c 2}
(into  () (rest '(0 1 2 3 4)))          ;-=> (4 3 2 1)