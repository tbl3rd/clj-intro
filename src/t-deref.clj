;;
;; DEREF
;;

;; Clojure values are immutable but sometimes you need to model change
;; in your system.  For that Clojure provides references.

;; A reference is a place for a value to be.  The value in that place
;; can change over time, but the values themselves never change.

;; The DEREF form yields the value in a reference.

(let [x (ref :value)]
  (deref x))                            ;-=> :value

;; As you might expect by now, there is also a reader macro for DEREF.

(let [x (ref :value)]
  @x)                                   ;-=> :value

;; The value in a REF can be changed only within a transaction bounded
;; by a DOSYNC form.  DOSYNC comprises zero or more REF changes (via
;; ALTER, COMMUTE, or REF-SET) guaranteed to atomically succeed or
;; fail together.  REF and DOSYNC allow for atomic, consistent, and
;; isolated coordination of changes from multiple threads across
;; multiple places.

;; DOSYNC is a flexible way to synchronize concurrent changes in
;; multiple REFs, but Clojure also provides ATOM, AGENT, and FUTURE
;; when you don't need all that flexibility.

(def deposits
  "Map depositor names to depositors."
  (ref {}))

(defn add-depositor
  "Add a depositor with NAME and BALANCE."
  [name balance]
  (let [depositor {:name name :balance (ref balance)}]
    (set-validator! (:balance depositor) (complement neg?))
    (dosync (alter deposits assoc (:name depositor) depositor))
    depositor))

(add-depositor "Tom"   9999)
(add-depositor "Akash" 9999)
(add-depositor "Kunal" 9999)

(defmacro do-or-catch
  "Result of evaluating expressions or message of any exception thrown."
  [& expressions]
  `(try (do ~@expressions)
        (catch Exception x#
          (.getMessage x#))))

(defn show-deposits
  []
  (into {} (for [{:keys [name balance]} (vals @deposits)]
             [name @balance])))

(show-deposits)

(defn transfer
  [amount from to]
  (dosync
   (let [deposits (ensure deposits)
         from (deposits from)
         to (deposits to)]
     (when (and from to)
       (commute (:balance from) - amount)
       (commute (:balance to)   + amount)))))

(transfer 99 "Tom" "Kunal")

(show-deposits)
