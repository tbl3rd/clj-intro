#!/usr/bin/env boot

;; See: https://github.com/boot-clj/boot/wiki/Boot-Environment
;;
(set-env!
 :resource-paths #{"src"}
 :target-path "target"
 :dependencies '[[org.clojure/clojure "1.7.0"]])
