(ns amithgeorge.fractions-test
  (:require [clojure.test :refer [deftest testing is]]
            [amithgeorge.fractions :as sut]))

(deftest add-fractions
  (testing "adding 0 to whole numbers"
    (is (= 0 (sut/add 0 0)))
    (is (= 1 (sut/add 0 1)))
    (is (= 2 (sut/add 2 0)))
    (is (= -1 (sut/add -1 0)))
    (is (= -2 (sut/add 0 -2))))

  (testing "adding whole numbers"
    (is (= 9 (sut/add 4 5)))
    (is (= -7 (sut/add -3 -4)))
    (is (= -5 (sut/add -7 2))))

  (testing "adding fractions with same denominator"
    (is (= (sut/->fraction 3 5)
           (sut/add (sut/->fraction 1 5)
                    (sut/->fraction 2 5))))

    (is (= (sut/->fraction -2 5)
           (sut/add (sut/->fraction -3 5)
                    (sut/->fraction 1 5))))

    (testing "resultant fraction should be in reduced form"
      (is (= (sut/->fraction 1 2)
             (sut/add (sut/->fraction 3 8)
                      (sut/->fraction 1 8))))
      (is (= (sut/->fraction 1 2)
             (sut/add (sut/->fraction -1 4)
                      (sut/->fraction 3 4))))
      (is (= (sut/->fraction 1 2)
             (sut/add (sut/->fraction 3 4)
                      (sut/->fraction -1 4)))))

    (testing "return whole number if resultant denominator is 1"
      (is (= 1
             (sut/add (sut/->fraction 1 3)
                      (sut/->fraction 2 3))))))

  (testing "adding fractions with different denominators"
    (is (= (sut/->fraction 11 8)
           (sut/add (sut/->fraction 3 4)
                    (sut/->fraction 5 8))))

    (is (= (sut/->fraction 11 18)
           (sut/add (sut/->fraction 1 6)
                    (sut/->fraction 4 9))))

    (is (= (sut/->fraction -11 15)
           (sut/add (sut/->fraction -4 5)
                    (sut/->fraction 1 15)))))

  (testing "adding fraction and whole number"
    (is (= (sut/->fraction 3 2)
           (sut/add 1
                    (sut/->fraction 1 2))))

    (is (= (sut/->fraction 23 5)
           (sut/add (sut/->fraction 3 5)
                    4)))

    (is (= (sut/->fraction -29 9)
           (sut/add (sut/->fraction 7 9)
                    -4)))

    (is (= (sut/->fraction 1 3)
           (sut/add (sut/->fraction -2 3)
                    1))))

  (testing "adding fractions with negative denominator"
    (is (= (sut/->fraction -1 -2)
           (sut/add (sut/->fraction 1 -4)
                    (sut/->fraction -3 -4))))

    (is (= 1
           (sut/add (sut/->fraction 1 4)
                    (sut/->fraction -3 -4))))

    (testing "return whole number if resultant denominator is -1"
      (is (= -1 (sut/add (sut/->fraction 1 -4)
                         (sut/->fraction 3 -4))))

      (is (= -1 (sut/add 0 (sut/->fraction 1 -1))))

      (is (= -1 (sut/add (sut/->fraction 0 -1)
                         (sut/->fraction 1 -1))))

      (is (= 3 (sut/add (sut/->fraction 3 -1)
                        (sut/->fraction 6 1)))))))


