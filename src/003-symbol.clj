;;
;; Every valid Clojure EXPRESSION has a VALUE.
;;

;; When Clojure reads an EXPRESSION, it either prints that
;; expression's VALUE or it throws an exception.

;; So far, the value of each expression that Clojure prints looks a
;; lot like the expression itself.

;; When Clojure reads a number, it prints that number's value, which
;; looks just like the number.  22/7 goes in and 22/7 comes out.

;; You send a vector of strings in and get one back.  You send in a
;; set of integers and get an equivalent back.  And so on ...
;;
;; There are two kinds of expressions with values that do not print
;; like they are read.

;; One kind is a SCALAR and another is a COLLECTION.
;;
;; The SCALAR is called a SYMBOL.  The COLLECTION is a LIST.

;; A symbol acts like a reserved word or 'variable' in languages like
;; Java.  It is a space-delimited token that does not begin with a
;; backslash (which would denote a character), a digit (which would be
;; a number) a colon (which would make it a keyword), any of the
;; COLLECTION delimiter characters ()"[]{}, for example, which would
;; make it a collection or something), or a quote (like ' or `).

;; You can't use `, ^, or ~ in a symbol, and a symbol cannot begin
;; with # or @ because those too are special to the mysterious READER.

;; A & by itself is also special and not a symbol.

;; We saw a symbol in the title.  Remember fn?

fn?                                     ; is a symbol
string? number? char? keyword? symbol?  ; all symbols
vector? map? set? list?                 ; more symbols

str char keyword symbol list            ; ? isn't needed
vec vector map set first rest seq       ; also symbols
*agent* *ns* pop! swap! partition-by    ; more symbols
= + - / not= == > < <= >= +' -' *'      ; yet more symbols

;; Symbols are used to name values.  Symbols also name types.

java.lang.String, clojure.lang.Ratio    ; These name types!

;; So even types are values in Clojure.

;; The value of a symbol is the value that symbol names.

;; Evaluating a symbol that has no value is an error though.

;; Clojure defines all the above symbols for you, so ... no errors.
