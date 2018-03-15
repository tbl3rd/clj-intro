;;
;; Clojure also has COLLECTIONS, which nest however you want.
;;

"String is kind of collection."         ; sequence of characters
[ 23 :is "a" :number]                   ; vector: indexed sequential
{ "key" "value" :kvs "..." }            ; map: associative by key
#{ 24 :member "..." }                   ; set: members in or out

;; Clojure's data language of SCALARS and COLLECTIONS encompasses
;; all of the textual data representations in common use today.

"Clojure"                               ; \C is at 0 and \e at 6.

["Clojure", 0 :to fn?]                  ; a tuple of scalars
[        0  1   2   3]                  ; indexed by integer

[:vmext:RegisterVimServerParams {:id 7} ; XML
 [:vmext:VimServer :name "dvc1-name"
  [:Description "Got me ..."]
  [:vmext:Username "lyonst"]
  [:vmext:Password "password"]
  [:vmext:Url "https://127.0.0.1"]
  [:vmext:IsEnabled true]]
 [:vmext:ShieldManager :name "vsm1"
  [:vmext:Username "vsm1-user"]
  [:vmext:Password "vsm1-pass"]
  [:vmext:Url "https://127.0.0.1"]]]

[4 [2 [0 1] [3]] [7 [5 6] [8 9]]]       ; vector of ints and vectors

[             4                         ; That vector ...
 [     2                                ;
  [0 1] [3]              ]              ; ... represents ...
 [                7                     ;
,,,,,,,,,,,,,[5 6] [8 9]]]              ; ... a binary tree.

{:Title "Montenegro"                    ; This map ...
 :Year 1981                             ;
 :Rated :R                              ; ... represents ...
 :Released "09 Oct 1981"                ;
 :Director "Dušan Makavejev"}           ; ... an object or 'dict'.

{:A :T, :C :G, :T :A, :G :C}            ; a DNA base pairing map

#{"Chico" "Groucho" "Harpo" "Zeppo"}    ; The set of Marx Brothers

{[247711 128745 615585] #{ 7 20}        ; This map of ...
 [724475 209311 596076] #{47 35}        ;
 [398807 687106 953042] #{ 5 51}        ; ... vector to set ...
 [ 49338 359939 906095] #{61   }        ;
 [571521 682416 785036] #{86 51}        ; ... represents ...
 [174818 239856 437406] #{ 6 22}        ;
 [535936 885819 472347] #{74  9}        ; ... a sparse matrix.
 [ 70989 376305 883810] #{26 68}}       ; And so on ...

{:safe   #{:get     :head               ; Map of "safe" ...
           :options :trace}             ; ... and "unsafe" ...
 :unsafe #{:delete  :patch              ; ... http methods.
           :post    :put}}

[:html5                                 ; HTML
 [:head
  [:title "Home | Compojure Docs"]]
 [:body
  [:div {:class "container-fluid"}
   [:div {:class "row-fluid"}
    [:div {:class "span2 menu"}]
    [:div {:class "span10 content"}
     [:h1 "Compojure Docs"]
     [:ul
      [:li [:a {:href "/getting-started"} "Getting Started"]]
      [:li [:a {:href "/routes-in-detail"} "Routes in Detail"]]
      [:li [:a {:href "/nesting-routes"} "Nesting Routes"]]]]]]]]

;; And, by the way, every collection in Clojure is true.
;; Even an empty collection is true.

"" [] {} #{} [nil] {nil ""} #{nil}      ; are all TRUE!

;; And there is one more collection to be introduced later.
