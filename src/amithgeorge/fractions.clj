(ns amithgeorge.fractions)

(defn- gcd
  [a b]
  (let [remainder (mod a b)]
    (if (pos? remainder)
      (recur b remainder)
      b)))

(comment
  (= 3 (gcd 6 9))
  (= 3 (gcd 9 6))
  (= 1 (gcd 7 5)))

(defn ->fraction
  [numerator denominator]
  {:numerator numerator
   :denominator denominator})

(defn- reduce-fraction [result]
  (let [result-gcf (gcd (:numerator result) (:denominator result))]
    (if (= 1 result-gcf)
      result
      (->fraction (/ (:numerator result) result-gcf)
                  (/ (:denominator result) result-gcf)))))

(defn fraction-is-whole? [reduced-fraction]
  (= 1 (:denominator reduced-fraction)))

(defn add
  [x y]
  (let [x (if (int? x) (->fraction x 1) x)
        y (if (int? y) (->fraction y 1) y)]
    (if (and (map? x)
             (map? y))
      (let [result (if (= (:denominator x) (:denominator y))
                     (->fraction (+ (:numerator x) (:numerator y))
                                 (:denominator x))
                     (let [denominator-lcm (/ (* (:denominator x)
                                                 (:denominator y))
                                              (gcd (:denominator x) (:denominator y)))
                           x-multiplier (/ denominator-lcm (:denominator x))
                           y-muliplier (/ denominator-lcm (:denominator y))
                           x' (->fraction (* x-multiplier (:numerator x))
                                          (* x-multiplier (:denominator x)))
                           y' (->fraction (* y-muliplier (:numerator y))
                                          (* y-muliplier (:denominator y)))]
                       (->fraction (+ (:numerator x') (:numerator y'))
                                   (:denominator x'))))
            reduced-fraction (reduce-fraction result)]
        (if (fraction-is-whole? reduced-fraction)
          (:numerator reduced-fraction)
          reduced-fraction))
      (+ x y))))