;;
;; a macro to catch exceptions and return NIL
;;

;; We want something to postpone having to deal with Java exceptions
;; where they are inconvenient.

;; SLURP is just Java's java.io.Reader::read under the covers.

(def mary
  "The text of 'Mary had a little lamb'. "
  (slurp "mary.txt"))

;; So SLURP can throw java.io.IOException in various ways.  For
;; example, trying to SLURP a file that is missing will throw a
;; java.io.FileNotFoundException.

(comment
  "This will throw java.io.FileNotFoundException!"
  (def fnord
    "The content of a missing file."
    (slurp "fnord.txt")))

;; That can be inconvenient.  Can't we just return NIL?

(comment
  "Want a DO-OR-NIL that returns NIL when exception thrown."
  (def slurps
    (do-or-nil
     (slurp "mary.txt")                 ; OK but ignored
     (slurp "fnord.txt")))              ; throws
  "In other words ..."
  (do-or-nil ...)
  "... results in something like this."
  (try (do ...)
       (catch Exception x
         (println "Caught:" x)))
  "where ... is any number of expressions."
  )

;; We can write a helper function.  Notice the (apply list ...).

(defn do-or-nil-fn
  [& expressions]
  (list 'try (apply list 'do expressions)
        (list 'catch 'Exception 'x
              (list 'println "Caught" 'x))))

;; APPLY is a function that takes a function as its first argument and
;; applies that function to the rest of the arguments.

(apply list 'do 0 1 2 (list 3 4 5))     ;-=> (do 0 1 2 3 4 5)

;; APPLY effectively splices any trailing list into the argument list
;; of its function.

(do-or-nil-fn '(slurp "mary.txt") '(slurp "fnord.txt"))

(try
  (do
    (slurp "mary.txt")
    (slurp "fnord.txt"))
  (catch Exception x
    (println "Caught" x)))

(defmacro do-or-nil
  "Value of BODY or nil if it throws."
  [& body]
  `(try (do ~@body)
        (catch Exception x#
          (println (str the-name ":") x#))))
