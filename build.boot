#!/usr/bin/env boot

;; See: https://github.com/boot-clj/boot/wiki/Boot-Environment
;;
(set-env!
 :resource-paths #{"src"}
 :target-path "target"
 :dependencies '[[org.clojure/clojure "1.8.0"]])

;; Evaluate in cider REPL to enable debugger.
;;
(boot.core/load-data-readers!)
