;;
;; What are we missing?
;;

;; You can write real programs with what's been explained so far, but
;; if you are already a programmer, you may have noticed some things
;; are missing.  What do you think is missing?  Let's discuss three
;; here: assignment, equality, and loops.

;; Assignment

;; Most programming languages in common use today have something
;; called "assignment" -- often denoted with single '=' equal sign.
;; Generally assignment lets you change the value named by a variable
;; or stored in a data structure.  As explained before: Clojure's
;; bindings and values are immutable, and '=' denotes "equality".

;; Where other languages let you change the value of a variable, a
;; Clojure VAR isn't a variable.  Instead of re-using a variable by
;; changing a value, a program binds a new symbol to a new value.

;; Clojure has a SET! primitive but it is so restricted that it is
;; used mostly to commune with the host language.  Both Java and
;; JavaScript have assignment, and their libraries often rely on it.
;; Lack of assignment would hamper Clojure's access to its host
;; language and its libraries, so Clojure has SET!.

;; SET! can also be used to establish aspects of the Clojure runtime
;; environment -- usually for development convenience.  For example:

(set! *print-length* 99)

;; limits the number of items of each collection the printer will
;; output.  The "*earmuffs*" on the *PRINT-LENGTH* symbol indicate
;; that it is "dynamic" and designed for use with SET!.

;; That exclamation mark at the end of SET! implies that something
;; imperative is happening.  Clojure has other core functions and
;; macros that end in '!' that we will meet later.  Just as '?'
;; implies a Boolean-valued function or "predicate", the '!' implies
;; that the thing named has a side-effect -- that something unusual to
;; Clojure is happening.  These are naming conventions -- not syntax
;; special to the language.

;; Equality

;; Most programming languages have some notion of "equality", but it
;; can sometimes be a squishy one.  Java, for example, has '==' and
;; 'Object::equals'.  Usually '==' is just syntax for a call to the
;; 'equals()' method on an object.  The 'equals()' method can be
;; inherited or overridden. Java distinguishes between object and
;; scalar equality.  By default, objects compare equal only when they
;; are the same object, but two separate scalars compare equal when
;; they have the same value, and so on.

;; In a pure Clojure program, equality (denoted by '=') is simple.
;; Scalars are equal when they print the same.  The same is true,
;; generally, for collections.  One exception is that Clojure does not
;; distinguish vectors from lists when testing equality: they are both
;; considered ordered sequences of values and compared index by index.
;; Two sequences are equivalent when they have the same length and
;; equivalent values at each position.

(= '(:first :second) '(:first :second))  ;=> true, of course
(=  [:first :second]  [:first :second])  ;=> true, of course
(= '(:first :second)  [:first :second])  ;=> true, but weird?

;; Expressions compare equal when their values are equal.  And since
;; Clojure values and bindings are immutable, they never transition
;; from one state to the other: once equal, always equal.  There is no
;; assignment to muddy the water.

;; In the following examples, BEATLES equals the set even though the
;; set prints differently from that used in the definition.  The
;; semantics of sets means that the order of elements is irrelevant to
;; the value of the set.

(def beatles #{:john :paul :george :ringo}) ;=> #'user/beatles
beatles                                     ;=> #{:george :ringo :paul :john}
(= beatles #{:george :ringo :paul :john})   ;=> true
(= beatles #{:george :john :paul :ringo})   ;=> true
(= beatles (into #{} (shuffle beatles)))    ;=> true

;; Clojure also has '==' to compare numbers for "mathematical
;; equality".  Use '==' when you know you're comparing numbers and
;; your code will probably run a bit faster with fewer surprises.

(=  1 1/1)                              ;=> true
(=  1 1.0)                              ;=> false
(== 1 1.0 1/1)                          ;=> true

;; Also note that if you use Clojure to access the host language
;; directly, you have to live by its equality rules.

;; Loops

;; Clojure has loops.  It even has FOR loops.  FOR looks a lot like
;; LET except that where LET binds a single value to a symbol, FOR
;; binds its symbols to sequences of values.  The result is also a
;; sequence of expressions, where each expression has the form
;; specified in the body of the FOR.

;; For example, you can read the following as: For H taking each of
;; :a and :b, let T take the values 0, 1, and 2, generating the
;; sequence of pairs [H T].

(for [h [:a :b]
      t [0 1 2]]
  [h t])               ;=> ([:a 0] [:a 1] [:a 2] [:b 0] [:b 1] [:b 2])

;; Notice that a later binding always exhausts its sequence of values
;; before an earlier symbol binds its next value.

;; As in the "for comprehensions" of other F-word languages, Clojure's
;; FOR bindings can be modified by guard expressions.

;; The :WHEN guard produces the body value only when its condition is
;; TRUE. The :WHILE guard generates body values until its condition is
;; FALSE.  FOR also supports a :LET form which introduces a new non-
;; sequenced binding as in the LET form.  They work together like
;; this.

(for [x (range)
      :when (odd? x)
      :let [squared (* x x)]
      :while (< squared 99)]
  squared)                              ;=> (1 9 25 49 81)

;; But as in most F-word, and especially L-word, languages FOR is
;; conceptually just convenient syntax built on top of FN and RECUR.

;; F-word programmers into category theory might say that FOR is
;; Clojure's "sequence monad" or "list monad".  (And I suspect it
;; is why Clojure treats both vectors and lists as sequences when
;; comparing them for equality!)
