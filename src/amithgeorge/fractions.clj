(ns amithgeorge.fractions)

(defn- gcd
  [a b]
  (let [a (Math/abs a)
        b (Math/abs b)
        remainder (mod a b)]
    (if (pos? remainder)
      (recur b remainder)
      b)))

(comment
  (= 3 (gcd 6 9))
  (= 3 (gcd 9 6))
  (= 1 (gcd 7 5))
  (= 2 (gcd -2 -4))
  (= 4 (gcd 4 -4)))

(defn- lcm
  [a b]
  (/ (Math/abs (* a b)) (gcd a b)))

(comment
  (= 4 (lcm 2 4))
  (= 18 (lcm 6 9))
  (= 4 (lcm -4 4))
  (= 6 (lcm -2 -3)))

(defn ->fraction
  [numerator denominator]
  (when (zero? denominator)
    (throw (IllegalArgumentException. "Denominator cannot be 0")))
  (let [[numerator' denominator'] (if (and (neg? numerator)
                                           (neg? denominator))
                                    [(- numerator) (- denominator)]
                                    [numerator denominator])]
    {:numerator numerator'
     :denominator denominator'}))

(comment
  (= {:numerator 3 :denominator 4} (->fraction 3 4))
  (= {:numerator 3 :denominator 4} (->fraction -3 -4))
  (= {:numerator -3 :denominator 4} (->fraction -3 4))
  (= {:numerator 3 :denominator -4} (->fraction 3 -4))
  (= :error (try
              (->fraction 2 0)
              (catch IllegalArgumentException e
                (prn (ex-message e))
                :error))))

(defn- reduce-fraction [result]
  (let [result-gcf (gcd (:numerator result) (:denominator result))]
    (if (= 1 result-gcf)
      result
      (->fraction (/ (:numerator result) result-gcf)
                  (/ (:denominator result) result-gcf)))))

(comment
  (= (->fraction 1 2) (reduce-fraction (->fraction 2 4)))
  (= (->fraction -1 2) (reduce-fraction (->fraction -2 4)))
  (= (->fraction 1 -2) (reduce-fraction (->fraction 2 -4))))

(defn- fraction-is-whole? [reduced-fraction]
  (= 1 (Math/abs (:denominator reduced-fraction))))

(defn- ->fractions-with-same-denominator
  [x y]
  (if (= (:denominator x) (:denominator y))
    [x y]
    (let [denominator-lcm (lcm (:denominator x) (:denominator y))
          x-multiplier (/ denominator-lcm (:denominator x))
          y-muliplier (/ denominator-lcm (:denominator y))
          x' (->fraction (* x-multiplier (:numerator x))
                         (* x-multiplier (:denominator x)))
          y' (->fraction (* y-muliplier (:numerator y))
                         (* y-muliplier (:denominator y)))]
      [x' y'])))

(comment
  (= [(->fraction 1 5) (->fraction 3 5)]
     (->fractions-with-same-denominator (->fraction 1 5) (->fraction 3 5)))

  (= [(->fraction 5 15) (->fraction 9 15)]
     (->fractions-with-same-denominator (->fraction 1 3) (->fraction 3 5))))

(defn- simplify-fraction [reduced-fraction]
  (if (fraction-is-whole? reduced-fraction)
    (:numerator reduced-fraction)
    reduced-fraction))

(defn- add-same-denominator-fractions [x' y']
  (->fraction (+ (:numerator x') (:numerator y'))
              (:denominator x')))

(defn- fraction-like?
  [x]
  (cond
    (int? x) true
    (and (map? x)
         (int? (:numerator x))
         (and (int? (:denominator x))
              (not (zero? (:denominator x))))) true
    :else false))

(defn add
  [x y]
  {:pre [(fraction-like? x) (fraction-like? y)]}
  (let [x (if (int? x) (->fraction x 1) x)
        y (if (int? y) (->fraction y 1) y)]
    (->> (->fractions-with-same-denominator x y)
         (apply add-same-denominator-fractions)
         reduce-fraction
         simplify-fraction)))

(comment
  (add 1 (->fraction 0 2)))