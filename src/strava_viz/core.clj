(ns strava-viz.core
  (:require [strava-viz.fetch :refer [get-and-format-runs]]
            [strava-viz.templating :refer [render-strava]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]))

(defn coerce-route [d m y]
  (let [day (Integer. d)
        month (Integer. m)
        year (Integer. y)]
    (render-strava (get-and-format-runs year month day))))

(defroutes handler
  (GET "/" [] (render-strava (get-and-format-runs)))
  (GET "/:d{\\d+}-:m{\\d+}-:y{\\d+}" [d m y] (coerce-route d m y)))

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
