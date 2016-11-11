;;
;; Namespaces
;;

;; In Clojure, that global implicit LET that DEF adds bindings to is
;; called a NAMESPACE.

;; You can think of a namespace as a global binding vector where DEF
;; stashes symbols and their values.

;; So far, we've been working in the USER namespace created by the
;; Clojure REPL for us to experiment in.  The value of the current
;; namespace is always bound to the symbol *NS*.

*ns*                                    ;-=> #namespace[boot.user]
(ns-name *ns*)                          ;-=> boot.user

;; The NS-NAME function returns the name of its namespace argument.
;; Note that the name of a namespace is a symbol.

;; The CREATE-NS function makes a new namespace, and IN-NS changes the
;; namespace bound to *NS* such that new DEF forms add bindings to it.

(create-ns 'new-namespace)              ;-=> #namespace[new-namespace]
(in-ns 'new-namespace)                  ;-=> #namespace[new-namespace]
*ns*                                    ;-=> #namespace[new-namespace]

;; Since we are now in NEW-NAMESPACE, we must reference the symbols
;; DEFined in our prior namespace, named BOOT.USER, explicitly because
;; there is no ADD1 symbol bound in NEW-NAMESPACE yet.

boot.user/add1                          ;-=> #function[boot.user/add1]
(boot.user/add1 3)                      ;-=> 4

;; Of course we can DEFine a new symbol ADD1 in this namespace and it
;; has nothing to do with an ADD1 bound in another namespace.

(def add1 "Not a function!")            ;-=> #'new-namespace/add1
add1                                    ;-=> "Not a function!"
new-namespace/add1                      ;-=> "Not a function!"
boot.user/add1                          ;-=> #function[boot.user/add1]

;; The part to the left of the / in the symbol is its namespace.  When
;; evaluated, the symbol to the right of the / is evaluated in the
;; context of the namespace to the left.

;; Similarly, keywords can also be bound to a particular namespace.

:global                                 ;-=> :global
:new-namespace/local                    ;-=> :new-namespace/local
::local                                 ;-=> :new-namespace/local

;; The double-colon in ::local is just an abbreviation for "the
;; keyword :local in the current namespace".

;; A binding in a namespace is sometimes called a VAR, which is that
;; weird #'NAMESPACE/SYMBOL thing you see printed as the value of a
;; DEF expression.

;; Namespaces are the main unit of code organization in Clojure.

;; By default, each namespace gets symbols from the clojure.core
;; and the java.lang namespaces when created.

=                                       ;-=> #function[clojure.core/=]
String                                  ;-=> java.lang.String

;; Any other symbol known to the program can be referenced explicitly
;; with a NAMESPACE/NAME symbol.

;; An NS expression determines which external symbols are bound in a
;; local namespace.

;; Namespaces allow Clojure programs to use libraries and grow without
;; code in one part of the program interfering with, perhaps unknown,
;; code in another part.  (They are used for organizing like packages
;; in Java or modules in other languages.)

;; Let's return now to our prior namespace with IN-NS.

(in-ns (quote boot.user))               ;-=> #namespace[boot.user]
*ns*                                    ;-=> #namespace[boot.user]
add1                                    ;-=> #function[boot.user/add1]
