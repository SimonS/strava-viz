(ns strava-viz.templating
  (:require [net.cgrand.enlive-html :as html]))

(html/deftemplate strava-week "week.html" [runs]
  [:tbody :tr] (html/clone-for [run runs]
                               [:tr [:td (html/nth-child 1)]] (html/content (:name run))
                               [:tr [:td (html/nth-child 2)]] (html/content (:start_date_local run))
                               [:tr [:td (html/nth-child 3)]] (html/content (:end_date_local run))
                               [:tr :td :a] (html/content (str (:id run)))
                               [:tr :td :a] (html/set-attr :href (str "https://www.strava.com/activities/" (:id run)))))

(defn render-strava [runs]
  (->> runs
       strava-week
       (apply str)))
