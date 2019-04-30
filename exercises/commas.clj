(ns commas
  "CODES WITHOUT COMMAS -- with apologies to F.H.C. Crick et al")

;; Bold and intriguing without being click-baity.
;; Fast, Cheap, and Out of Control
;; Not snory like these.
;;
["Recursive functions of symbolic expressions
 and their computation by machine Part I"
 "Deceleration heating of interplanetary dust in the Earth's
 atmosphere and its simulation using analog materials"]

{1957 ["Eisenhower starts second term by suspending nuclear tests."
       "Chuck Berry records 'Rock and Roll Music'."
       "The Cavern Club opens and Lennon meets McCartney."
       "'Throne of Blood' released."
       "Wham-O ships the first Frisbee."
       "IBM ships the first FORTRAN compiler."
       "Brooklyn Dodgers move to Los Angeles."
       "USAF drops a hydrogen bomb on Albuquerque New Mexico."
       "FBI arrests James Riddle Hoffa."
       "Flooding in Japan kills 1000 people."
       "The International Geophysical Year begins."
       "'Perry Mason' and 'Leave It to Beaver' premier on TV."
       "NAACP registers Little Rock Nine honor students."
       "United States Commission on Civil Rights established."
       "A nuclear reactor blows up in Chelyabinsk USSR."
       "2 Sputniks and Laika orbit Earth starting 'space race'."
       "'West Side Story' opens on Broadway."
       "US Army Captain Hank Cramer killed in Vietnam."
       "Toyota begins exporting to the US."
       "Boeing 707 flies for the first time."
       "'Atlas Shrugged' and 'The Cat in the Hat' published."
       "John von Neumann dies."
       "Osama bin Laden is born."]}

(comment "http://www.pnas.org/content/43/5/416" is the paper.)

"It is assumed in one of the more popular theories of protein
 synthesis that amino acids are ordered on a nucleic acid strand
 and that the order of the amino acids is determined by the order
 of the nucleotides of the nucleic acid."

(comment Now is 1957.  What does the RNA Tie Club know?)

(def nucleotides ["Adenine" "Cytosine" "Guanine" "Thymine"])
nucleotides

(def ACGT (map first nucleotides))
ACGT

(def pair (zipmap ACGT (reverse ACGT)))
pair

(def strand (repeatedly (fn [] (rand-nth ACGT))))
strand

(def dna (map (fn [b] [b (pair b)]) strand))
dna

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

(def dispensible {:A "alanine"
                  :D "aspartic acid"
                  :E "glutamic acid"
                  :N "asparagine"
                  :S "serine"})

(def amino (merge essential conditional dispensible))

"The problem of how a sequence of four things (nucleotides) can
determine a sequence of twenty things (amino acids) is known as
the 'coding' problem."

(count nucleotides)
(count amino)

(comment Now ... What can we find out?)

;; If each amino acid were coded by two bases, we should only be
;; able to code 4 X 4 = 16 amino acids. It is natural, therefore,
;; to consider nonoverlapping codes in which three bases code
;; each amino acid.

(def the-singles (for [x ACGT] [x]))
(count the-singles)
(def the-doubles (for [x ACGT y ACGT] [x y]))
(count the-doubles)
(def the-triples (for [x ACGT y ACGT z ACGT] [x y z]))
(count the-triples)

["This confronts us with two difficulties:"
 1 "Since there are 4 x 4 x 4 = 64 different triplets of four
    nucleotides why are there not 64 kinds of amino acids?"
 2 "In reading the code how does one know how to choose the
    groups of three?"]

(def string (partial apply str))
(def triples (map string (for [x ACGT y ACGT z ACGT] [x y z])))

"We shall assume that there are certain sequences of three
 nucleotides with which an amino acid can be associated and
 certain others for which this is not possible."

"... we say that some of the 64 triplets make sense and some
 make nonsense. We further assume that all possible sequences
 of the amino acids may occur ... and that at every point in
 the string of letters one can only read 'sense' in the
 correct way."

;; In other words, any two triplets which make sense can be put
;; side by side, and yet the overlapping triplets so formed
;; must always be nonsense.

"... consider for the moment the restrictions imposed by
 placing each amino acid next to itself."

[:sense    "...  CAT  AAA  AAA  TAG  ..."]
[:nonsense "...   ATA  AAA  AAT      ..."]
[:nonsense "...    TAA  AAA  ATA     ..."]

;; It is easy to see that the 60 remaining triplets can be
;; grouped into 20 sets of three, each set of three being
;; cyclic permutations of one another.

(take (count ACGT) (partition (count ACGT) 1 (cycle ACGT)))

(def rotations
  (fn [s] (let [n (count s)]
            (map string (take n (partition n 1 (cycle s)))))))

(def codons (set (map (comp set rotations) triples)))

(def sense-codons ((group-by count codons) 3))

(def sense (map (comp first sort) sense-codons))
(count sense)

(def nonsense (remove (set sense) (set triples)))
(count nonsense)

(def code (zipmap (map vec (sort sense)) (sort (keys amino))))

(map string (take 7 (partition 3 1 strand)))

(def amino-keys (keep code (partition 3 1 strand)))

(def aminos (map amino amino-keys))

;; The example given here is only for illustration,
;; but it brings out the physical idea behind the concept
;; of a comma-less code.

"Note: This is fun programming but (as of 1961) bad biology."

(comment Questions?)
