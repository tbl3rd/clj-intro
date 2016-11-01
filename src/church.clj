(def T (fn [t e] t))                    ; true
(def F (fn [t e] e))                    ; false
(def IF (fn [b t e] (b t e)))
(IF T "true" "false")                   ;-=> "true"
(IF F "true" "false")                   ;-=> "false"
