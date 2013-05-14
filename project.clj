(defproject server-client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                [org.webbitserver/webbit "0.4.14"]
                [org.clojure/data.json "0.2.0"]
                [org.clojars.houshuang/keymaster-clj "0.1.0"]]
  :main server-client.core
  :jvm-opts ~(vec (map (fn [[p v]] (str "-D" (name p) "=" v))
                       {:jna.nosys true})) )
