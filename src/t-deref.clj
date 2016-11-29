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

(def accounts
  "Map depositor IDs to depositors."
  (ref {:deposits    {:id :deposits    :name "deposits"    :balance (ref 0M)}
        :withdrawals {:id :withdrawals :name "withdrawals" :balance (ref 0M)}}))

(let [account-id (ref 100000)]
  (defn next-account-id
    []
    (dosync (alter account-id inc))))

(defn add-depositor
  "Add a depositor with NAME and BALANCE."
  [name balance]
  (let [depositor {:id (next-account-id) :name name :balance (ref balance)}]
    (set-validator! (:balance depositor) (complement neg?))
    (dosync (alter accounts assoc (:id depositor) depositor))
    depositor))

(add-depositor "Tom"   9999M)
(add-depositor "Akash" 9999M)
(add-depositor "Kunal" 9999M)

(defmacro do-or-catch
  "Result of evaluating expressions or message of any exception thrown."
  [& expressions]
  `(try (do ~@expressions)
        (catch Exception x#
          (.getMessage x#))))

(defn show-accounts
  []
  (vec (sort-by
        second
        (for [{:keys [id name balance]} (vals @accounts)]
          [id name @balance]))))

(show-accounts)

(defn lookup-id
  [name]
  (letfn [(find-name [account] (= (:name account) name))]
    (if-let [accounts (filter find-name (vals @accounts))]
      (when (= 1 (count accounts))
        (first accounts)))))

(lookup-id "Akash")

(defn transfer
  [amount from to]
  (dosync
   (let [accounts (ensure accounts)
         from     (lookup-id from)
         to       (lookup-id to)]
     (when (and from to)
       (commute (:balance to)   + amount)
       (commute (:balance from) - amount)))))

(defn adjust
  [name op amount]
  (dosync
   (when-let [account (lookup-id name)]
     (commute (:balance account) op amount))))

(defn deposit
  [name amount]
  (adjust name + amount))

(defn withdraw
  [name amount]
  (adjust name - amount))

(comment
  (transfer 99 "Tom" "Kunal")
  #_(show-accounts)
  #_(do-or-catch
     (transfer 9999 "Tom" "Kunal"))
  )

(show-accounts)
