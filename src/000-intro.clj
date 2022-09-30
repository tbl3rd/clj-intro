["Clojure", 0 :to fn?]                  ; an introduction

;;-=> ["Clojure" 0 :to #function[clojure.core/fn?]]

"Where ;-=> means 'has the value'.  That's just me, not Clojure."

"What happened to the '; an introduction'?"

"Everything from"  \; "to the end of a line is a comment."

;; Notice that ';-=>' is a comment too, which is why I chose it.

"What happened to the" \, \?

\,\s :are :whitespace :in :Clojure :.

,,[,,, "Clojure",,, 0, :to,,fn? ,,],, ; Sprinkle them around as you like.

;;-=> ["Clojure" 0 :to #function[clojure.core/fn?]]

;; And what are the rest of those things?

;; BTW: All of the files are valid (if mostly useless) programs.
;; You can load and evaluate them in your IDE or editor, and I
;; encourage you to do that.


;; Note to the reader:

;; These files are lecture notes.  They do not stand alone.

;; But I can't be everywhere and talk to everyone, so hope
;; these comments can launch the reader's own discovery.

;; Clojure is often called "a functional Lisp on the JVM".

;; That is clear enough if you know what the describer means
;; by "functional" and "Lisp", but I think it kind of misses
;; the rare value of a language like Clojure.  And even though
;; Clojure is a "hosted" language, you don't have to understand
;; Java or the JVM to use it.

;; Here is another way to think about Clojure.

;; Clojure is a language for describing and manipulating data.

;; I hope this introduction demonstrates the value of that.

;; For those who know what "functional" and "Lisp" and maybe
;; even "monad" means, it can be useful to use those terms.

;; This introduction refers to those terms as the "F-word",
;; the "L-word", and the "M-word", respectively.

;; My "T-word" terminology is meant in fun and to emphasize that it
;; is not important to understand the concepts named by those terms
;; to "get" Clojure and understand how to use it effectively.
