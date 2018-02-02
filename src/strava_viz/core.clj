(ns strava-viz.core
  (:require [strava-viz.fetch :refer [formatted-runs]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [net.cgrand.enlive-html :as html]))

(html/deftemplate strava-week "week.html" [runs]
  [:tbody :tr] (html/clone-for [run runs]
                               [:tr [:td (html/nth-child 1)]] (html/content (:name run))
                               [:tr [:td (html/nth-child 2)]] (html/content (:start_date_local run))
                               [:tr [:td (html/nth-child 3)]] (html/content (:end_date_local run))
                               [:tr :td :a] (html/content (str (:id run)))
                               [:tr :td :a] (html/set-attr :href (str "https://www.strava.com/activities/" (:id run)))))

(defn handler [request]
  {:status 200
   :body (apply str (strava-week formatted-runs))
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
