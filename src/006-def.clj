;;
;; DEF and NS
;;

;; Where other languages have declarations and definitions, Clojure
;; uses a special list to name things.  The head of that list is DEF
;; for DEFINE.

;; DEF binds a symbol to a value.  Here we bind the value 23 to the
;; symbol TWENTY-THREE.

(def twenty-three 23)                   ;-=> #'user/twenty-three
twenty-three                            ;-=> 23

;; Here we bind the symbol ODDS to all the odd integers and TWO-ODDS
;; to the first 2 ODDS.

(def odds (filter odd? (range)))        ;-=> #'user/odds
(def two-odds (take 2 odds))            ;-=> #'user/two-odds
two-odds                                ;-=> (1 3)

;; Here we bind the symbol LOJURE to a vector of characters.

(def lojure (vec (rest "Clojure")))     ;-=> #'user/lojure
lojure                                  ;-=> [\l \o \j \u \r \e]

;; The place where DEF symbols are bound is called a NAMESPACE.

;; You can think of a namespace as a global map where DEF stashes
;; symbols keyed to their values.

;; So far, we've been working in the USER namespace created by the
;; Clojure Read-Eval Print Loop (REPL) for us to experiment in.  The
;; value of the current namespace is always bound to the symbol *NS*.

*ns*                                    ;-=> #namespace[user]
(ns-name *ns*)                          ;-=> user

;; The NS-NAME function returns the name of its namespace argument.
;; Note that the name of a namespace is a symbol.

;; The binding of a symbol to a value in a namespace is sometimes
;; called a VAR.  The VAR is that weird value the REPL prints after
;; evaluating a DEF form.

(def twenty-three 23)                   ;-=> #'user/twenty-three

;; DEF is a macro over VAR.  The first argument to DEF is always a
;; symbol.  That symbol is bound to the following value in a VAR and
;; stored in the current namespace.

;; The part to the left of the / in the VAR is its namespace.  When
;; evaluated, the symbol to the right of the / is evaluated in the
;; context of the namespace to the left.  We say "the value 23 is
;; bound to the symbol TWENTY-THREE in the USER namespace".

;; Similar to symbols, keywords can also be bound to a particular
;; namespace.

:global                                 ;-=> :global
:user/local                             ;-=> :user/local
::local                                 ;-=> :user/local

;; The double-colon in ::local is just an abbreviation for "the
;; keyword :local in the current namespace".  The value of ::local
;; depends on the namespace in which it is evaluated, as opposed
;; to :global whose value is the same :global everywhere.

;; Namespaces are the main unit of code organization in Clojure.

;; By default, each namespace gets symbols from the clojure.core
;; and the java.lang namespaces when created.

=                                       ;-=> #function[clojure.core/=]
String                                  ;-=> java.lang.String

;; Any other symbol known to the program can be referenced explicitly
;; with a NAMESPACE/NAME symbol.

;; Clojure defines an NS macro to specify and determine which symbols
;; are bound in a namespace.

;; Namespaces allow Clojure programs to use libraries and grow without
;; code in one part of the program interfering with, perhaps unknown,
;; code in another part.  (They are used for organizing code like
;; packages in Java or modules in other languages.)
