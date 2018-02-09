#!/usr/bin/env boot

;; See: https://github.com/boot-clj/boot/wiki/Boot-Environment
;;
(set-env!
 :resource-paths #{"src"}
 :target-path "target"
 :dependencies '[[org.clojure/clojure "1.9.0"]
                 [onetom/boot-lein-generate "0.1.3"]])

(require 'boot.lein)

(deftask lein
  "Generate a lein project.clj file for Cursive, and so on."
  []
  (with-pre-wrap fileset
    (boot.lein/generate)
    fileset))

(boot (lein))                           ; Always refresh project.clj.
