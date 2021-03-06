;;
;; DEREF
;;

;; Clojure values are immutable but sometimes you need to model change
;; in your system.  For that Clojure provides references.

;; Unlike a symbol which just names a value, a reference is a place
;; for a value to be.  The value in that place can change over time,
;; but the values themselves never change.  There are many kinds of
;; reference (AGENT ATOM DELAY FUTURE REF), but the DEREF function
;; works on all of them and yields the value in the reference.

;; A reference is designed to be shared across threads, but they are
;; useful even in single-threaded applications.  Clojure ensures that
;; DEREF shows a consistent view of the value in all threads that
;; share the reference.

(let [at (agent  :agent)
      am (atom   :atom)
      dy (delay  :delay)
      fe (future :future)
      rf (ref    :ref)]
  (vector (deref at)
          (deref am)
          (deref dy)
          (deref fe)
          (deref rf)))         ;-=> [:agent :atom :delay :future :ref]

;; As you might expect by now, there is also a reader macro for DEREF.

(let [at (agent  :agent)
      am (atom   :atom)
      dy (delay  :delay)
      fe (future :future)
      rf (ref    :ref)]
  (vector @at @am @dy @fe @rf)) ;-=> [:agent :atom :delay :future :ref]

;; DELAY is like DO except that the expressions in the DELAY are not
;; evaluated until the DELAY is DEREFed.  From that point on, the
;; DELAY is just a reference to a single value: the result of
;; evaluating the expressions.  You can use DELAY to postpone an
;; expensive computation until its value is needed.  This is like
;; "call by need" in Algol-like languages and is the default "lazy"
;; evaluation model of some F-word languages like Haskell.

(let [delayed (delay (println "Delayed!") :delayed)
      done (do (println "Done!") :done)]
  (vector @delayed @delayed done))      ;-=> [:delayed :delayed :done]

;; Notice that "Done!" prints before "Delayed!" and that "Delayed!"
;; prints once and not twice.

;; FUTURE is like DELAY except that the expressions are evaluated in
;; the background and DEREF blocks until the value is available.

(letfn [(make [x] (future (Thread/sleep 3000) (println x) x))]
  (time (vec (map deref (map make (range 10))))))
                                        ;-=> [0 1 2 3 4 5 6 7 8 9]

;; Notice that the result shows in about 3 (instead of 30) seconds,
;; and that the integers are printed in some apparently random order
;; whereas the vector result shows the expected order.  That's because
;; the future computations are doled out to background threads which
;; run and print their integers concurrently.  Only when all the
;; futures finish are their values collected in the result vector.

;; An ATOM is a reference whose value can be changed synchronously and
;; atomically from one or more threads via SWAP!.  SWAP! atomically
;; applies a function to the current value of the ATOM to leave a new
;; value in the ATOM.  The function is applied via COMPARE-AND-SET!
;; and is retried as necessary until the SWAP! succeeds with the new
;; value in the ATOM.  (Therefore, your SWAP! function should be
;; idempotent, or otherwise free of side-effects, or you will have a
;; bad time.)

(let [ints (atom [])
      make (fn [x] (future (Thread/sleep 100) (swap! ints conj x)))
      results (map deref (map make (range 5)))]
  (time {:sorted (vec (sort-by count results))
         :results (vec results)}))

;;-=> {:sorted  [[4] [4 0] [4 0 3] [4 0 3 1] [4 0 3 1 2]],
;;     :results [[4 0] [4 0 3 1] [4 0 3 1 2] [4 0 3] [4]]}

;; Threads add their integers and report results in apparently random
;; order, but ATOM ensures that every integer is present and none is
;; duplicated.

;; An AGENT is an asynchronous ATOM that is managed by another thread
;; or thread pool.

;; SEND or SEND-OFF adds an update request to an AGENT's queue.  The
;; AGENT serially dispatches requests as threads become available to
;; service them, where they run asynchronously but atomically against
;; the value in the AGENT.

;; An update requested by SEND runs opportunistically on threads
;; allocated from a pool.  A SEND-OFF request allocates its own thread
;; to run so that IO doesn't starve AGENTs of updates.  Unlike SWAP!,
;; SEND and SEND-OFF are asynchronous and so just immediately return
;; their AGENT -- not the value in it.

;; Agents support an API for monitoring AGENT state, and managing the
;; thread pools that service them.  There are calls to wait for and
;; validate updates, detect, manage and report errors, and to restart
;; failed AGENTs.  Here is a small example of what's available.

(let [ints (agent [])
      updates (atom [])
      watch (fn [k r o n] (swap! updates conj o))
      make (fn [x] (send ints conj x))]
  (add-watch ints :watch watch)
  (dorun (map make (range 5)))
  (await ints)
  (remove-watch ints :watch)
  {:updates @updates
   :ints @ints})

;;=> {:updates [[] [0] [0 1] [0 1 2] [0 1 2 3] [0 1 2 3 4]],
;;    :ints [0 1 2 3 4]}

;; Agents are Clojure's take on the Actor model of computing, and too
;; large a subject to exhaust here.

;; A REF is a reference whose value can be changed only in the context
;; of a transaction established by DOSYNC.  Here's an example of using
;; ATOM and REF to model bank accounts -- the canonical example of
;; stateful transactions in practice.

;; SET-VALIDATOR! attaches a validator to a reference.  A validator is
;; a predicate function applied to the state resulting from any
;; alteration of the reference's value.  The alteration fails (rolling
;; back the value) and throws an IllegalStateException when the
;; validator returns FALSE.

(def accounts (ref {}))

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
  (do-or-catch
   (transfer 9999 "Tom" "Kunal"))
  )

(show-accounts)

;; References break down into two separate groups: DELAY and FUTURE,
;; or AGENT, ATOM, and REF.

;; DELAY and FUTURE take multiple expressions like DO, but return
;; immediately with a reference instead of the result of evaluating
;; the expressions.  FUTURE evaluates expressions in the background
;; asynchronously.  DELAY evaluates its expressions synchronously, but
;; only when demanded by DEREF.  DELAY and FUTURE are useful when the
;; caller can continue without the result of an evaluation.  Use DELAY
;; when the resulting value may not be needed.  Use FUTURE when the
;; value's necessity is not in doubt.

;; AGENT, ATOM, and REF add degrees of coordination to the synchronous
;; versus asynchronous behavior of DELAY and FUTURE.  Like DELAY and
;; FUTURE, AGENT and ATOM operate on single values in isolation.  REF,
;; on the other hand, coordinates multiple value changes atomically
;; across references in the context of an DOSYNC transaction.

;; The following table summarizes reference semantics.

;;    |--------------+--------+---------------+-------------|
;;    | AND          |        | UNCOORDINATED | COORDINATED |
;;    |--------------+--------+---------------+-------------|
;;    | SYNCHRONOUS  | delay  | atom          | ref         |
;;    |--------------+--------+---------------+-------------|
;;    | ASYNCHRONOUS | future | agent         |             |
;;    |--------------+--------+---------------+-------------|

;; What might an asynchronous and coordinated reference look like?

;; There is one other kind of reference called a VAR.  VARs are used
;; to implement host bindings and the thread-local dynamic bindings
;; changed by SET!.  For example, the DEF form is a macro over VAR.
;; The mutability of VAR is how the REPL supports the redefinition of
;; symbols during interactive development.
