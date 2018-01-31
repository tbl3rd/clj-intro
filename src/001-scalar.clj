;;
;; SCALARS
;;

;; These literals are sometimes called SCALARS to distinguish them
;; from COLLECTIONS.

"Clojure" "is a" java.lang.String       ; made of characters

\T \h \e \s \e \space \a \r \e \space java.lang.Character \. \newline

\tab "and" \newline "are" \space "also" \space java.lang.Character \.

0, 23, -42, 3.14159, 6e23, 22/7         ; are all numbers

22/7 "is a" clojure.lang.Ratio

314159265358.14159265358M "is a" java.math.BigDecimal

12345678901234567890N "is a" clojure.lang.BigInt

:to :is :a clojure.lang.Keyword         ; Keywords begin with a colon.

false :and true :are java.lang.Boolean

nil                                     ; nil is Java's null.

nil "is just" nil "In Clojure, it represents 'nothing'."

;; In Boolean contexts, the value of NIL and FALSE are both false.
;; In Boolean contexts, everything other than NIL and FALSE is true.

nil, false                              ;-=> both FALSE
true, 0, "", :false, "false", :nil      ;-=> all TRUE!

"Clojure represents" java.util.regex.Pattern "values as ..."

#"..."                           ; matches any 3 characters
#"\w+"                           ; matches 1 or more "word characters"

;; And there is one other kind of scalar to be introduced later.
