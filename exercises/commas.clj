(ns commas
  "Codes Without Commas -- with apologies to Francis Crick"
  (:require [clojure.set :as s]
            [clojure.math.combinatorics :as c]))

(def bases ["Adenine" "Cytosine" "Guanine" "Thymine"])

(def ACGT (map first bases))
ACGT                                    ; => (\A \C \G \T)

(def pair (zipmap acgt (reverse ACGT)))
pair                                    ; => {\A \T \C \G \G \C \T \A}

(def rna (repeatedly (fn [] (rand-nth ACGT))))

(take 7 rna)                            ; => (\G \G \C \T \G \C \C)

(def dna (map (fn [b] [b (pair b)]) rna))

(take 7 dna) ; => ([\G \C] [\C \G] [\T \A] [\T \A] [\A \T] [\A \T] [\G \C])
(take 7 (map first  dna))               ; => (\G \C \T \T \A \A \G)
(take 7 (map second dna))               ; => (\C \G \A \A \T \T \C)

(def essential
  {:F "phenylalanine"
   :H "histidine"
   :I "isoleucine"
   :K "lysine"
   :L "leucine"
   :M "methionine"
   :T "threonine"
   :V "valine"
   :W "tryptophan"})

(def conditional
  {:C "cysteine"
   :G "glycine"
   :P "proline"
   :Q "glutamine"
   :R "arginine"
   :Y "tyrosine"})

(def dispensible
  {:A "alanine"
   :D "aspartic acid"
   :E "glutamic acid"
   :N "asparagine"
   :S "serine"})

(def amino (merge essential conditional dispensible))

(count amino)                           ; => 20

(c/selections ACGT 2)                   ; => ((\A \A)
                                        ;     (\A \C)
                                        ;     (\A \G)
                                        ;     (\A \T)
                                        ;     (\C \A)
                                        ;     (\C \C)
                                        ;     (\C \G)
                                        ;     (\C \T)
                                        ;     (\G \A)
                                        ;     (\G \C)
                                        ;     (\G \G)
                                        ;     (\G \T)
                                        ;     (\T \A)
                                        ;     (\T \C)
                                        ;     (\T \G)
                                        ;     (\T \T))

(count (c/selections ACGT 2))                   ; => 16
(count (c/selections ACGT 3))                   ; => 64
(> (count (c/selections ACGT 2)) (count amino)) ; => false
(> (count (c/selections ACGT 3)) (count amino)) ; => true

(def string (partial apply str))

(def triples (map string (c/selections ACGT 3)))

(take 7 triples)           ; => ("AAA" "AAC" "AAG" "AAT" "ACA" "ACC" "ACG")
(take 7 (reverse triples)) ; => ("TTT" "TTG" "TTC" "TTA" "TGT" "TGG" "TGC")
(count triples)            ; => 64

(def rotations
  (fn [s]
    (let [n (count s)]
      (map string
           (take n (partition n 1 (cycle s)))))))

(rotations ACGT)                    ; => ("ACGT" "CGTA" "GTAC" "TACG")
(rotations (take 3 ACGT))           ; => ("ACG" "CGA" "GAC")

(take 7 (map rotations triples))        ; => (("AAA" "AAA" "AAA")
                                        ;     ("AAC" "ACA" "CAA")
                                        ;     ("AAG" "AGA" "GAA")
                                        ;     ("AAT" "ATA" "TAA")
                                        ;     ("ACA" "CAA" "AAC")
                                        ;     ("ACC" "CCA" "CAC")
                                        ;     ("ACG" "CGA" "GAC"))

(def codons (set (map (comp set rotations) triples)))

(count codons)                          ; => 24
(take 7 codons)                         ; => (#{"ACC" "CCA" "CAC"}
                                        ;     #{"GGG"}
                                        ;     #{"TTT"}
                                        ;     #{"GCC" "CGC" "CCG"}
                                        ;     #{"CAA" "ACA" "AAC"}
                                        ;     #{"CTC" "CCT" "TCC"}
                                        ;     #{"AGC" "CAG" "GCA"})

(count (group-by count codons))         ; => 2
(map first (group-by count codons))     ; => (3 1)

(def sense-groups (filter (fn [g] (= 3 (count g))) codons))

(count sense-groups)                    ; => 20 Eureka!

(take 7 sense-groups)                   ; => (#{"ACC" "CCA" "CAC"}
                                        ;     #{"GCC" "CGC" "CCG"}
                                        ;     #{"CAA" "ACA" "AAC"}
                                        ;     #{"CTC" "CCT" "TCC"}
                                        ;     #{"AGC" "CAG" "GCA"}
                                        ;     #{"TAT" "TTA" "ATT"}
                                        ;     #{"GAG" "GGA" "AGG"})

(def sense-codons (map (comp first sort) sense-groups))

(count sense-codons)   ; => 20

(take 7 sense-codons)  ; => ("ACC" "CCG" "AAC" "CCT" "AGC" "ATT" "AGG")

(def nonsense-codons (s/difference (set triples) (set sense-codons)))

(count nonsense-codons)                 ; => 44

(= (count triples)
   (+ (count sense-codons)
      (count nonsense-codons)))         ; => true

(def code (zipmap (map vec (sort sense-codons)) (sort (keys amino))))

(sort-by second code)                   ; => ([[\A \A \C] :A]
                                        ;     [[\A \A \G] :C]
                                        ;     [[\A \A \T] :D]
                                        ;     [[\A \C \C] :E]
                                        ;     [[\A \C \G] :F]
                                        ;     [[\A \C \T] :G]
                                        ;     [[\A \G \C] :H]
                                        ;     [[\A \G \G] :I]
                                        ;     [[\A \G \T] :K]
                                        ;     [[\A \T \C] :L]
                                        ;     [[\A \T \G] :M]
                                        ;     [[\A \T \T] :N]
                                        ;     [[\C \C \G] :P]
                                        ;     [[\C \C \T] :Q]
                                        ;     [[\C \G \G] :R]
                                        ;     [[\C \G \T] :S]
                                        ;     [[\C \T \G] :T]
                                        ;     [[\C \T \T] :V]
                                        ;     [[\G \G \T] :W]
                                        ;     [[\G \T \T] :Y])

(map string (take 7 (partition 3 rna)))
;; => ("CAA" "ACA" "TAG" "TTC" "AAA" "CTG" "CTA")

(def amino-keys (remove nil? (map code (partition 3 rna))))

(take 7 amino-keys)                     ; => (:L :Y :E :F :G :D :M)

(def aminos (map amino amino-keys))

(take 23 aminos)                        ; => ("valine"
                                        ;     "glutamic acid"
                                        ;     "cysteine"
                                        ;     "valine"
                                        ;     "valine"
                                        ;     "proline"
                                        ;     "aspartic acid"
                                        ;     "cysteine"
                                        ;     "arginine"
                                        ;     "phenylalanine"
                                        ;     "tryptophan"
                                        ;     "isoleucine"
                                        ;     "cysteine"
                                        ;     "leucine"
                                        ;     "glycine"
                                        ;     "leucine"
                                        ;     "proline"
                                        ;     "histidine"
                                        ;     "proline"
                                        ;     "tryptophan"
                                        ;     "glutamine"
                                        ;     "leucine"
                                        ;     "glutamine")

"... and not a single comma in sight"

"Note: This is fun programming but (as of 1961) bad biology."
