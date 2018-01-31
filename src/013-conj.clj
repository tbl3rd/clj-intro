;;
;; CONJuring with collections
;;

;; You know how to write down collections as literal values.
;; You know how to take them apart with FIRST and REST.
;; You know how to restore them from sequences with INTO.

;; CONJ returns a new collection with values added.

(conj  [] 0 1 2 3)                       ;-=>  [0 1 2 3]
(conj  () 0 1 2 3)                       ;-=>  (3 2 1 0)
(conj #{} 0 1 2 3)                       ;-=> #{0 1 3 2}
(conj  {} [:a 0] [:b 1] [:c 2])          ;-=>  {:a 0, :b 1, :c 2}

;; CONJ adds elements where appropriate for the collection.

(conj  [0 1 2] 3)                       ;-=>  [0 1 2 3]
(conj '(0 1 2) 3)                       ;-=>  (3 0 1 2)
(conj #{0 1 2} 3)                       ;-=> #{0 1 3 2}
(conj  {:a 0 :b 0} [:c 0])              ;-=>  {:a 0, :b 0, :c 0}

;; CONJ adds to a vector at the tail.
;; CONJ adds to a list at the head.
;; CONJ adds to a set or map wherever.

;; INTO uses CONJ to add values to a collection, so (into () ...)
;; effectively reverses the sequence of values in its arguments.

;; The words "adds to" do not mean that CONJ changes the collection.

;; The value CONJ produces is always a new collection containing
;; values from the old collection along with the argument values.

;; You cannot change a collection value any more than you can change
;; an integer value.

;; (add1 22) adds the value 1 to the value 22 to produce the new value
;; 23.  (add1 22) does not change the value of the integer 22 to 23.

;; Similarly, (conj [0 1 2] 3) does not change the value of [0 1 2]
;; to [0 1 2 3].  It adds the value 3 to the value [0 1 2] to produce
;; the new value [0 1 2 3].  That is easy to demonstrate.

(let [old [0 1 2]]
  {:new (conj old 3), :old old})   ;-=> {:new [0 1 2 3], :old [0 1 2]}

;; This applies even to infinite sequences.

(let [whole (range)]
  {:now (take 3 (conj whole -1))
   :was (take 3 whole)
   :new (take 3 whole)
   :and (take 3 whole)})

;;-=> {:now (-1 0 1), :was (0 1 2), :new (0 1 2), :and (0 1 2)}

;; A sequence is a list, so CONJ adds to its head, but WHOLE is always
;; the whole numbers so (take 3 whole) always produces the same list
;; of 3 integers.  CONJ does not add to that list of integers, nor
;; does TAKE consume from them.

;; This is not just true of CONJ and collections.  The same is true
;; for any function applied to any value.  Functions take old values
;; and produce new values leaving the old values unchanged.

;; Let that sink in for a while ...
;; It is an important principle in F-word programming.
