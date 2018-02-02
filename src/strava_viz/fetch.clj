(ns strava-viz.fetch
  (:require [clj-strava.api :as strava]
            [clojure.core.async :refer [<!!]]
            [environ.core :refer [env]]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]))

(def tkn (env :strava-access-token))

(defn get-epochs-for-this-week [todays-date]
    (let [today (t/floor todays-date t/day)
          today-day (t/day-of-week today)
          monday-diff (- today-day 1)
          monday-date (t/minus- today (t/days monday-diff))
          next-monday-date (t/plus- monday-date (t/days 7))]
      [(tc/to-epoch monday-date)
       (tc/to-epoch next-monday-date)]))

(defn get-runs [monday next-monday]
  (<!! (strava/activities tkn {"before" next-monday "after" monday})))

(defn assoc-end-date [run]
  (assoc run :end_date_local
    (tc/to-string (t/plus-
                    (tc/from-string (:start_date_local run))
                    (t/seconds (Integer. (:elapsed_time run)))))))

(defn format-runs [runs] (map assoc-end-date runs))

(defn get-and-format-runs
  ([] (get-and-format-runs (t/now)))
  ([y m d] (get-and-format-runs (t/date-time y m d)))
  ([given-day] (let [[monday next-monday] (get-epochs-for-this-week given-day)]
    (format-runs
      (sort-by :start_date_local
               (filter (comp #{true} :commute) (get-runs monday next-monday)))))))
