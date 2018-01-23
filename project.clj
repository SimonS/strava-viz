(defproject strava-viz "0.1.0-SNAPSHOT"
  :description "Small webapp to display Strava commutes as timesheet. Mostly intended as play with Clojure and d3."
  :url "https://github.com/simons/strava-viz"
  :jvm-opts ^:replace []
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ekimber/clj-strava "0.1.1"]
                 [clj-time "0.14.2"]])
