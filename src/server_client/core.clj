(ns server-client.core
    (:use [server-client.hotkeys :only [bind-socket init-hotkeys]])
    (:require [clojure.data.json :as json])
    (:import [org.webbitserver WebServers WebSocketHandler]))


(defn -main []
    (let [port 8889]
        (doto (WebServers/createWebServer 8889)
            (.add "/"
                (proxy [WebSocketHandler] []
                    (onOpen [socket] (do
                            (println "opened" socket)
                            (bind-socket socket)))
                    (onClose [socket] (do
                            (println "closed" socket)
                            ()))
                    (onMessage [c j] ())))
            (.start))
        (init-hotkeys)
        (println "Started socket server on port" port)))
