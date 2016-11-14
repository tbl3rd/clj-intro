;;
;; Binding forms
;;

;; FN, LET, and DEF are known as "binding forms" because they bind
;; names (represented as symbols on the left) to values evaluated from
;; expressions on the right.  Clojure has a couple of others too.

;; So far, the left side of all our bindings have been simple symbols,
;; but Clojure also allows vector and map expressions on the left side
;; as the targets of most bindings.  Vectors of symbols bind sequence
;; values (such as vectors, lists and sequences) and maps bind
;; associative values such as maps and sets.

;; Here is a SWAP function, for example.

(defn swap [[x y]] [y x])               ; Bind a vector of 2 elements.
(swap [:left :right])                   ;-=> [:right :left]

;; Applying SWAP to [:left :right] binds X to :left and Y to :right.
;; Then the resulting vector swaps X and Y, swapping the values.
;; But any value can be bound this way.

(swap [[0 1] [2 3]])                    ;-=> [[2 3] [0 1]]
(swap ["left" {:a 0 :b 1}])             ;-=> [{:a 0, :b 1} "left"]

;; Bindings can nest.

(defn swamp
  [[a b] [x y]]
  [[y x] [b a]])

(swamp [0 1] [2 3])                     ;-=> [[3 2] [1 0]]

;; The keyword :as binds its following form to the original
;; expression's value.

(defn show-rotate
  [[a b c :as was]]
  [:was was :now [c a b]])

(show-rotate [0 1 2])                 ;-=> [:was [0 1 2] :now [2 0 1]]

;; A naked ampersand & is special in binding forms too.  It means bind
;; the values of all following expressions to the next expression as a
;; sequence.

(defn show-rotate-more
  [[a b c & more :as was]]
  [:more more :was was :now [c a b]])

(show-rotate-more [0 1 2 3 4])
;; -=> [:more (3 4) :was [0 1 2 3 4] :now [2 0 1]]

;; It is not unusual to see a variable number of arguments bound to a
;; single parameter as a list using &.  The most common case is MAIN.

(defn main [& args]
  [:args args])

(main "echo" "-n" "No" "newline" "here.")
;;-=> [:args ("echo" "-n" "No" "newline" "here.")]

(defn really-rotate
  "Bind ARGS to a SEQuence of all the arguments."
  [& args]
  (vec (cons (last args) (butlast args))))

(really-rotate 0 1 2 3 4)               ;-=> [4 0 1 2 3]

;; A map binding behaves kind of like keyword arguments in languages
;; like Python.

(defn ->person
  [{name :name birth :birth}]
  {:kind :person :name name :birth (or birth :unknownn)})

(->person {:name "Tom"})
;;-=> {:kind :person, :name "Tom", :birth :unknownn}

;; You can even supply default arguments value with :or.

(defn ->person
  [{name :name birth :birth :or {birth :unknown}}]
  {:kind :person :name name :birth birth})

(->person {:name "Tom"})
;;-=> {:kind :person, :name "Tom", :birth :unknownn}

;; But again, as often in Clojure, the {foo :foo bar :bar} pattern is
;; so common that there is a simpler way to write it with a keyword.

(defn ->person
  [{:keys [name birth]}]
  {:kind :person :name name :birth (or birth :unknownn)})

(->person {:name "Tom"})

;; There are also the much less common :syms and :strs keywords in map
;; bindings, which bind map and set values whose keys are symbols or
;; strings.

;; Destructuring is implemented in FN, so it is available in most of
;; the binding forms: FN, LET, DEFN, DEFMACRO, LOOP, FOR, BINDING, and
;; so on -- even binding forms you might write yourself.

;; One exception is DEF which can bind only a symbol.  That is because
;; the Clojure implementation needs to be able to create Java objects
;; to bootstrap its implementation, and DEF is where that happens
;; before there is enough Clojure to support FN binding.
