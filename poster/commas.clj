(ns commas
  "CODES WITHOUT COMMAS -- with apologies to F.H.C. Crick et al")

(comment "http://www.pnas.org/content/43/5/416" is the paper.
         The year is 1957. What do we know now?)

(def nucleotides ["Adenine" "Cytosine" "Guanine" "Thymine"])
nucleotides ; => ["Adenine" "Cytosine" "Guanine" "Thymine"]
(count nucleotides)                     ; => 4

(def essential   {:F "phenylalanine"
                  :H "histidine"
                  :I "isoleucine"
                  :K "lysine"
                  :L "leucine"
                  :M "methionine"
                  :T "threonine"
                  :V "valine"
                  :W "tryptophan"})

(def conditional {:C "cysteine"
                  :G "glycine"
                  :P "proline"
                  :Q "glutamine"
                  :R "arginine"
                  :Y "tyrosine"})

(def dispensable {:A "alanine"
                  :D "aspartic acid"
                  :E "glutamic acid"
                  :N "asparagine"
                  :S "serine"})

(def amino-acids (merge essential conditional dispensable))
(count amino-acids)                     ; => 20

"The problem of how a sequence of four things (nucleotides)
 can determine a sequence of twenty things (amino acids)
 is known as the 'coding' problem."

(comment Now ... given that. What can we find out?)

;; BREAK

"Begin digression: Model base pairing with a simple Clojure map."

(str "Model" \space "base" \space "pairing" \.) ; => "Model base pairing."

(def sentence ["Model" \space "base" \space "pairing" \.])
sentence ; => ["Model" \space "base" \space "pairing" \.]

(apply str sentence)                            ; => "Model base pairing."
((partial apply str) sentence)                  ; => "Model base pairing."

(def string (partial apply str))
(string sentence)                               ; => "Model base pairing."

nucleotides            ; => ["Adenine" "Cytosine" "Guanine" "Thymine"]

(first nucleotides)                     ; => "Adenine"
(first (second nucleotides))            ; => \C
(map first nucleotides)                 ; => (\A \C \G \T)

(def ACGT (string (map first nucleotides)))
ACGT                                    ; => "ACGT"
(string (reverse ACGT))                 ; => "TGCA"

(def pair (zipmap ACGT (reverse ACGT)))
pair                                    ; => {\A \T \C \G \G \C \T \A}

(map pair ACGT)                         ; => (\T \G \C \A)

[(rand-nth ACGT) (rand-nth ACGT)]       ; => [\G \A]

(def strand (repeatedly (fn [] (rand-nth ACGT))))
(string (take 23 strand))               ; => "TCTGTCCCCGTAGACAAGACGTT"
(string (take 23 (map pair strand)))    ; => "AGACAGGGGCATCTGTTCTGCAA"

"End digression: Now where were we?" "We were counting things."

(count ACGT)                                 ; =>  4
(count amino-acids)                          ; => 20
(count (for [x ACGT]               [x]))     ; =>  4
(count (for [x ACGT y ACGT]        [x y]))   ; => 16
(count (for [x ACGT y ACGT z ACGT] [x y z])) ; => 64

;; BREAK

"Not enough selections taking nucleotides 2 at a time to cover the
aminos but more than enough taken 3 at a time. Call them triples."

(def triples (map string (for [x ACGT y ACGT z ACGT] [x y z])))
triples         ; => ["AAA" "AAC" "AAG" "AAT" "ACA" "ACC" "ACG" "ACT"
;;                    "AGA" "AGC" "AGG" "AGT" "ATA" "ATC" "ATG" "ATT"
;;                    "CAA" "CAC" "CAG" "CAT" "CCA" "CCC" "CCG" "CCT"
;;                    "CGA" "CGC" "CGG" "CGT" "CTA" "CTC" "CTG" "CTT"
;;                    "GAA" "GAC" "GAG" "GAT" "GCA" "GCC" "GCG" "GCT"
;;                    "GGA" "GGC" "GGG" "GGT" "GTA" "GTC" "GTG" "GTT"
;;                    "TAA" "TAC" "TAG" "TAT" "TCA" "TCC" "TCG" "TCT"
;;                    "TGA" "TGC" "TGG" "TGT" "TTA" "TTC" "TTG" "TTT"]

(def rotations
  (fn [s] (let [n (count s)]
            (map string (take n (partition n 1 (cycle s)))))))

(rotations ACGT)                        ; => ("ACGT" "CGTA" "GTAC" "TACG")

(take 7 (map rotations triples))        ; => (("AAA" "AAA" "AAA")
                                        ;     ("AAC" "ACA" "CAA")
                                        ;     ("AAG" "AGA" "GAA")
                                        ;     ("AAT" "ATA" "TAA")
                                        ;     ("ACA" "CAA" "AAC")
                                        ;     ("ACC" "CCA" "CAC")
                                        ;     ("ACG" "CGA" "GAC"))

(set ACGT)                              ; => #{\A \C \G \T}
[((set ACGT) \T) ((set ACGT) \Z)]       ; => [\T nil]

(def codons (set (map (comp set rotations) triples)))
(count codons)                          ; => 24
(take 4 codons)                         ; => (#{"ACC" "CCA" "CAC"}
                                        ;     #{"GGG"}
                                        ;     #{"TTT"}
                                        ;     #{"GCC" "CGC" "CCG"})

(map first (group-by count codons))     ; => (3 1)

(def sense-codons ((group-by count codons) 3))

(count sense-codons)                    ; => 20 Eureka!

;; BREAK

(take 5 sense-codons)                   ; => (#{"ACC" "CCA" "CAC"}
                                        ;     #{"GCC" "CGC" "CCG"}
                                        ;     #{"CAA" "ACA" "AAC"}
                                        ;     #{"CTC" "CCT" "TCC"}
                                        ;     #{"AGC" "CAG" "GCA"})

(def sense (map first sense-codons))
(count sense)   ; => 20
(take 7 sense)  ; => ("ACC" "GCC" "CAA" "CTC" "AGC" "TAT" "GAG")

(def nonsense (remove (set sense) (set triples)))
(count nonsense)                        ; => 44

(def code (zipmap sense (keys amino-acids)))
(sort-by second code)                   ; => (["AGC" :A]
                                        ;     ["TAA" :C]
                                        ;     ["TGT" :D]
                                        ;     ["TCA" :E]
                                        ;     ["TAT" :F]
                                        ;     ["TAG" :G]
                                        ;     ["CTG" :H]
                                        ;     ["CAA" :I]
                                        ;     ["ACG" :K]
                                        ;     ["ACC" :L]
                                        ;     ["GCC" :M]
                                        ;     ["GAA" :N]
                                        ;     ["CTA" :P]
                                        ;     ["GAT" :Q]
                                        ;     ["CTC" :R]
                                        ;     ["TCG" :S]
                                        ;     ["CTT" :T]
                                        ;     ["GTG" :V]
                                        ;     ["GAG" :W]
                                        ;     ["GGC" :Y])

(map string (take 3 (partition 3 1 strand))) ; => ("TTA" "TAA" "AAT")

(def amino-keys (map (comp code string) (partition 3 1 strand)))
(take 13 amino-keys) ; => (nil :C nil nil nil :Y nil nil :M nil nil :Y :M)

(def aminos (map amino-acids (remove nil? amino-keys)))
(take 4 aminos) ;=> ("cysteine" "tyrosine" "methionine" "tyrosine")

"This is fun programming but (as of 1961) bad biology."
"The Lisp programming language is as old as this paper."
"Lisp was designed to help machines help us think."
"A Lisp program is about the world more than itself."
"A program is just a list of expressions to be evaluated."
"Execution is just the sequence of such evaluations."
"Lisp is almost the simplest language that can work."
"You develop and explore a Lisp program while it runs."
"This program includes the values of most expressions."
"They appear literally as the computer prints them out."
"How can we do this with other programming languages?"
"(-: Did you notice any commas in the code? :-)"
