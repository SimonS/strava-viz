(ns strava-viz.core
  (:require [strava-viz.fetch :refer [runs]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [net.cgrand.enlive-html :as html]))

(html/deftemplate strava-week "week.html" [hdr] [:h1] (html/content hdr))

(defn handler [request]
  {:status 200
   :body (apply str (strava-week (str (count runs) " runs this week.")))
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
