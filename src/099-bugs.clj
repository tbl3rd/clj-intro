;; Sometime Clojure will let you down.

;; This file is a compilation of some of those times.

;; UPDATE-IN can surprise you when its key sequence is empty.
;;
;; https://dev.clojure.org/jira/browse/CLJ-1623
;;
(update-in {:a 0} [] dissoc :a)         ;=> {:a 0, nil nil}

;; MERGE is "just" CONJ under the covers.
;;
;; https://twitter.com/tbl3rd/status/1085737622104875009
;;
(merge [0 1] [2 3])                  ; => [0 1 [2 3]] ; WTF?
(merge {0 0 1 1} {0 2 1 3})          ; => {0 2, 1 3} ; OK
(conj  [0 1] [2 3])                  ; => [0 1 [2 3]] ; from the defn

;; Sometimes the underlying Java model pokes through in surprising
;; ways.
;;
;; https://twitter.com/tbl3rd/status/643543136409513984
;;
(comment
  "Both of these throw."
  (sort-by key {:a  0 "b" 1})
  (sort-by key {"a" 0 :b  1})
  )

;; Some values don't print like they read because their types differ.
;;
(keyword "k")                           ; => :k
(symbol ":k")                           ; => :k
(= (keyword "k") (symbol ":k"))         ; => false
(map type [(keyword "k") (symbol ":k")])
;; => (clojure.lang.Keyword clojure.lang.Symbol)

;; And some values are just different though they look the same.
;;
(keyword "b" "c")                       ; => :b/c
(keyword "b/c")                         ; => :b/c
(keyword nil "b/c")                     ; => :b/c
(= (keyword "b" "c") (keyword "b/c") (keyword nil "b/c"))
;; => false
(map type [(keyword "b" "c") (keyword "b/c") (keyword nil "b/c")])
;; => (clojure.lang.Keyword clojure.lang.Keyword clojure.lang.Keyword)
