(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]))

(def lib 'net.clojars.amithgeorge/fractions)
(def version "0.1.0-SNAPSHOT")
(def main 'amithgeorge.fractions)

(defn test
  "Run the tests."
  [opts]
  (bb/run-tests opts))

(defn uberjar
  "Create the uberjar"
  [opts]
  (-> opts
      (assoc :lib lib :version version :main main)
      (bb/clean)
      (bb/uber)))

(defn ci
  "Run the CI pipeline of tests (and build the uberjar)."
  [opts]
  (-> opts
      ;; (assoc :lib lib :version version :main main)
      (bb/run-tests)
      uberjar))

