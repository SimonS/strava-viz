(defproject strava-viz "0.1.0-SNAPSHOT"
  :description "Small webapp to display Strava commutes as timesheet. Mostly intended as play with Clojure and d3."
  :url "https://github.com/simons/strava-viz"
  :jvm-opts ^:replace []
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ekimber/clj-strava "0.1.1"]
                 [clj-time "0.14.2"]
                 [ring "1.6.3"]]
  :main strava-viz.core)
