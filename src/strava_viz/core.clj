(ns strava-viz.core
  (:require [clj-strava.api :as strava]
            [clojure.core.async :refer [<!!]]
            [environ.core :refer [env]]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]))

(def tkn (env :strava-access-token))

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

(def runs (let [[monday next-monday] (get-epochs-for-this-week (t/date-time 2018 01 23))]
  (filter
    (comp #{true} :commute)
    (<!! (strava/activities tkn {"before" next-monday "after" monday})))))

(defn handler [request]
  {:status 200
   :body (str (count runs) " runs this week!")
   :headers {}})

(defn -dev-main
  "auto reloads in dev"
  [port-number]
  (jetty/run-jetty
     (wrap-reload #'handler)
     {:port (Integer. port-number)}))

(defn -main
  [port-number]
  (jetty/run-jetty
     handler
     {:port (Integer. port-number)}))
