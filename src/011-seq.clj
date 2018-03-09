;;
;; Sequences
;;

;; Remember REST returns a collection without its head as a list.

;; But does this make sense to you?

(rest  "Clojure")                       ;-=> (\l \o \j \u \r \e)
(rest  [0 1 2 3 4])                     ;-=> (1 2 3 4)
(rest #{0 1 2 3 4})                     ;-=> (1 4 3 2)
(rest  {:a 0 :b 1 :c 2})                ;-=> ([:b 1] [:c 2])

(rest  "Clojure")                       ; Why not  "lojure"    ?
(rest  [0 1 2 3 4])                     ; Why not  [1 2 3 4]   ?
(rest #{0 1 2 3 4})                     ; Why not #{1 4 3 2}   ?
(rest  {:a 0 :b 1 :c 2})                ; Why not  {:b 1 :c 2} ?

;; REST actually produces a SEQUENCE.  A SEQUENCE prints as a list.

;; SEQ turns a collection into a sequence if it isn't already.

(seq "Clojure")                         ;-=> (\C \l \o \j \u \r \e)
(seq (seq "Clojure"))                   ;-=> (\C \l \o \j \u \r \e)
(seq (seq (seq "Clojure")))             ;-=> (\C \l \o \j \u \r \e)

;; SEQ turns a string into a sequence of its characters.
;; SEQ turns a vector into a sequence of its elements in order.
;; SEQ turns a set    into a sequence of its members in some order.
;; SEQ turns a map    into a sequence of its [key value] entries --
;;                                       again, in some order.

;; Many functions produce and consume sequences, which is why those
;; functions can operate on any Clojure collection.  Those functions
;; first call SEQ on their collection parameters.

;; One function that SEQ makes possible is RANGE.
;; RANGE returns a sequence of integers.

(range 10)                              ;-=> (0 1 2 3 4 5 6 7 8 9)
(range 3 8)                             ;-=> (3 4 5 6 7)
(range 0 10 3)                          ;-=> (0 3 6 9)
(def whole (range))                     ;-=> #'boot.user/whole

;; What is WHOLE?  WHOLE is the positive integers starting at 0.

(take 10 whole)                         ;-=> (0 1 2 3 4 5 6 7 8 9)
(take 3 (drop 99999 whole))             ;-=> (99999 100000 100001)

;; A sequence is a lazy list that can be arbitrarily long, even
;; infinite.  You get only what you take.  Lazy sequences mean a
;; large collection or a long sequence becomes just another value
;; in a program.  In other words, a Clojure program can process a
;; collection that is too large to fit in the program's memory.

;; INTO is one convenient way to turn a finite SEQ back into a
;; collection of whatever type you need.

(into  [] (rest  [0 1 2 3 4]))          ;-=>  [1 2 3 4]
(into #{} (rest #{0 1 2 3 4}))          ;-=> #{1 2 3 4}
(into  {} (rest  {:a 0 :b 1 :c 2}))     ;-=>  {:b 1, :c 2}
(into  () (rest '(0 1 2 3 4)))          ;-=>  (4 3 2 1)

;; This is so important that you should make sure you understand it
;; before continuing.  Play with collections and sequences in the
;; Clojure REPL.  When you believe you understand sequences, consider
;; the following question.

"Why does" (into () (rest '(0 1 2 3 4))) "reverse order?"

;; It's OK if you don't know because it's kind of weird.
