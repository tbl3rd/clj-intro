;;
;; Java
;;

;; Clojure is designed to inter-operate with Java conveniently.
;; We've seen some of this already.

;; Refer to a static field with Class/Field.

Math/E                                  ;-=> 2.718281828459045
Math/PI                                 ;-=> 3.141592653589793
String/CASE_INSENSITIVE_ORDER
;;-=> #object[java.lang.String$CaseInsensitiveComparator ...]

;; Call a static method.

(System/getenv "USER")                  ;-=> "tbl"

;; Call a method on an object like this (. object method arg0 arg1 ...)

(. "UPPER TO LOWER" toLowerCase)        ;-=> "upper to lower"
(. "UPPER TO LOWER" charAt 5)           ;-=> \space

;; There is a short-hand form as well.

(.toLowerCase "UPPER TO LOWER")         ;-=> "upper to lower"
(.length "UPPER TO LOWER")              ;-=> 14
(.charAt "UPPER TO LOWER" 5)            ;-=> \space

;; Clojure's NEW is Java's NEW.

(new java.util.Date)        ;-=> #inst "2016-11-21T19:50:46.795-00:00"

;; And there is this short-hand for calling a constructor.

(java.util.Date.)           ;-=> #inst "2016-11-21T19:51:01.947-00:00"

;; You can even access non-static fields this way.

(. (new java.awt.Point 2 3) -x)         ;-=> 2
(. (java.awt.Point. 2 3) -y)            ;-=> 3
(.-y (java.awt.Point. 2 3))             ;-=> 3
(.-x (java.awt.Point.))                 ;-=> 0

;; The .. form chains access across several methods and fields.

(. (. System (getProperties)) (get "os.name")) ;-=> "Mac OS X"
(.. System (getProperties) (get "os.name"))    ;-=> "Mac OS X"

;; The DOTO form passes an object along to following expressions.

(doto (java.util.HashMap.)
  (.put "a" 0)
  (.put "b" 1)
  (.put "c" 2)
  (println)) ;-=> #object[java.util.HashMap 0x481bbea2 {a=0, b=1, c=2}]

;; Below I put ,,,s where the HashMap object is slotted in.

(doto (java.util.HashMap.)
  (.put ,,, "a" 0)
  (.put ,,, "b" 1)
  (.put ,,, "c" 2)
  (println ,,,)) ;-=> #object[java.util.HashMap 0x481bbea2 {a=0, b=1, c=2}]

;; And of course TRY, CATCH, FINALLY, and THROW.

(try "fnord" (/ 1 0)
     (catch Exception e (println "Ignoring:" e))
     (finally (println :Oops!)))

;; There is a lot more to Java interop in Clojure -- most of which is
;; to support low-level access to Java primitives when you need it,
;; and you usually don't.

;; There are even MONITOR-ENTER and MONITOR-EXIT, as managed by the
;; LOCKING macro, which I've never used.  Please forget I mentioned
;; them.
