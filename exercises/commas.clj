(ns commas
  "Codes Without Commas -- with apologies to Francis Crick"
  (:require [clojure.set :as s]
            [clojure.math.combinatorics :as c]))

(def acgt "ACGT")
acgt                                    ; => "ACGT"

(def pair (zipmap acgt (reverse acgt)))
pair                                    ; => {\A \T \C \G \G \C \T \A}

(def rna (repeatedly (fn [] (rand-nth acgt))))

(take 7 rna)                            ; => (\G \G \C \T \G \C \C)

(def dna (map (fn [b] (str b (pair b))) rna))

(take 7 dna)                  ; => ("GC" "GC" "CG" "TA" "GC" "CG" "CG")
(take 7 (map first  dna))     ; => (\G \G \C \T \G \C \C)
(take 7 (map second dna))     ; => (\C \C \G \A \C \G \G)

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

(c/combinations acgt 2) ; => ((\A \C) (\A \G) (\A \T) (\C \G) (\C \T) (\G \T))

(count (c/selections acgt 2))                   ; => 16
(count (c/selections acgt 3))                   ; => 64
(> (count (c/selections acgt 2)) (count amino)) ; => false
(> (count (c/selections acgt 3)) (count amino)) ; => true

(def triples (map (partial apply str) (c/selections acgt 3)))

(take 7 triples)           ; => ("AAA" "AAC" "AAG" "AAT" "ACA" "ACC" "ACG")
(take 7 (reverse triples)) ; => ("TTT" "TTG" "TTC" "TTA" "TGT" "TGG" "TGC")
(count triples)            ; => 64

(defn rotations
  [s]
  (let [n (count s)]
    (map (partial apply str)
         (take n (partition n 1 (cycle s))))))

(rotations acgt)                    ; => ("ACGT" "CGTA" "GTAC" "TACG")
(rotations (take 3 acgt))           ; => ("ACG" "CGA" "GAC")

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

(def code (zipmap (sort sense-codons) (sort (keys amino))))

(sort-by second code)                   ; => (["AAC" :A]
                                        ;     ["AAG" :C]
                                        ;     ["AAT" :D]
                                        ;     ["ACC" :E]
                                        ;     ["ACG" :F]
                                        ;     ["ACT" :G]
                                        ;     ["AGC" :H]
                                        ;     ["AGG" :I]
                                        ;     ["AGT" :K]
                                        ;     ["ATC" :L]
                                        ;     ["ATG" :M]
                                        ;     ["ATT" :N]
                                        ;     ["CCG" :P]
                                        ;     ["CCT" :Q]
                                        ;     ["CGG" :R]
                                        ;     ["CGT" :S]
                                        ;     ["CTG" :T]
                                        ;     ["CTT" :V]
                                        ;     ["GGT" :W]
                                        ;     ["GTT" :Y])

(def base-triples (map (partial apply str) (partition 3 rna)))
(take 7 base-triples) ; => ("CAA" "ACA" "TAG" "TTC" "AAA" "CTG" "CTA")

(def amino-keys (remove nil? (map code base-triples)))

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

"NB: This is fun programming but bad biology."
