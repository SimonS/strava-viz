(ns strava-viz.core
  (:require [clj-strava.api :as strava]
            [clojure.core.async :refer [<!!]]
            [environ.core :refer [env]]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]))

(def tkn (strava/access-token (env :code)))

(defn get-epochs-for-this-week
  ([] (get-epochs-for-this-week (t/now)))
  ([todays-date]
    (let [today (t/floor todays-date t/day)
          today-day (t/day-of-week today)
          monday-diff (- today-day 1)
          monday-date (t/minus- today (t/days monday-diff))
          next-monday-date (t/plus- monday-date (t/days 7))]
      [(tc/to-epoch monday-date)
       (tc/to-epoch next-monday-date)])))


(t/date-time 2018 01 17)

(let [[monday next-monday] (get-epochs-for-this-week )]
  (<!! (strava/activities tkn {"before" next-monday "after" monday})))

