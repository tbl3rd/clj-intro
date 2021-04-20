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
    (slurp "fnord.txt"))
  )

;; That can be inconvenient.  Can't we just return NIL instead?

;; Remember that DO just evaluates its expressions in order and
;; "returns" the value of the last one.

(do 0 1 2 3 4)                          ;-=> 4

;; DO is useful mostly for side effects.

(do (println "First")
    (println "Second")
    (println "Third")
    :done)                              ;-=> :done

(comment
  "Want a DO-OR-NIL that returns NIL when an exception is thrown."
  (def slurps
    (do-or-nil
     (println (slurp "mary.txt"))       ; OK but ignored
     (println (slurp "fnord.txt"))))    ; throws
  "In other words ..."
  (do-or-nil ...)
  "... results in something like this."
  (try (do ...)
       (catch Exception x
         (println "Caught:" x)))
  "where ... is any number of expressions.")

;; We can write a helper function.  Notice the & and (apply list ...).

(defn do-or-nil-fn
  [& expressions]
  (list 'try (apply list 'do expressions)
        (list 'catch 'Exception 'x
              (list 'println "Caught:" 'x))))

;; APPLY is a function that takes another function as its first
;; argument and applies that function to the rest of the arguments.

(apply list 'do 0 1 2 (list 3 4 5))     ;-=> (do 0 1 2 3 4 5)

;; APPLY effectively splices any trailing list into the argument list
;; of its function.

(do-or-nil-fn
 '(println (slurp "mary.txt"))
 '(println (slurp "fnord.txt"))
 ':ok)

;; And evaluating that results in the expression we want.

(try
  (do
    (println (slurp "mary.txt"))
    (println (slurp "fnord.txt"))
    :ok)
  (catch Exception x
    (println "Caught:" x)))

;; We can wrap DO-OR-NIL-FN in a DO-OR-NIL macro to avoid the quoting.

(defmacro do-or-nil
  [& expressions]
  (apply do-or-nil-fn expressions))

;; But that encounters another problem we avoid with MACROEXPAND.

(macroexpand
 '(do-or-nil
   (println (slurp "mary.txt"))
   (println (slurp "fnord.txt"))
   :ok))

;; Do you see the problem in the result below?

(comment
  (try
    (do
      (println (slurp "mary.txt"))
      (println (slurp "fnord.txt"))
      :ok)
    (catch java.lang.Exception user/x
      (clojure.core/println "Caught:" user/x)))
  )

;; Our exception symbol X is interpreted as bound in the surrounding
;; USER namespace instead of as local to the macro expansion.
;; We can work around that with GENSYM (GENerate a SYMbol).

(gensym)                                ;-=> G__10912
(gensym)                                ;-=> G__10915

;; GENSYM generates a new symbol each time it is called.

(defn do-or-nil-fn
  [& expressions]
  (let [x (gensym)]
    (list 'try (apply list 'do expressions)
          (list 'catch 'Exception x
                (list 'println "Caught:" x)))))

;; After modifying our DO-OR-NIL-FN to bind X to a gensym, our
;; DO-OR-NIL macro is ready to go.

(defmacro do-or-nil
  [& expressions]
  (apply do-or-nil-fn expressions))

(macroexpand
 '(do-or-nil
   (println (slurp "mary.txt"))
   (println (slurp "fnord.txt"))
   :ok))

(do-or-nil
 (println (slurp "mary.txt"))
 (println (slurp "fnord.txt"))
 :ok)

;; And as you might suspect, there is more shorthand for both the
;; GENSYM binding and the list argument splicing.

;; X# ("gensym x") means generate a unique symbol and bind it to X.

;; And ~@ ("Unquote-Splicing") means splice the following list
;; expression into its surrounding list.

;; This is how a macro such as DO-OR-NIL is usually written with all
;; the GENSYM and Quasiquote shorthand in place.

(defmacro do-or-nil
  "Value of BODY or nil if it throws."
  [& body]
  `(try (do ~@body)
        (catch Exception x#
          (println "Caught:" x#))))

;; Try it out!

(do-or-nil
 (println (slurp "mary.txt"))
 :ok)

(do-or-nil
 (println (slurp "mary.txt"))
 (println (slurp "fnord.txt"))
 :ok)

;; As with Unquote, Unquote-Splicing can be used only within a
;; surrounding Quasiquote form.
