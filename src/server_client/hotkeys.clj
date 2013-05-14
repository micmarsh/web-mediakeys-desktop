(ns server-client.hotkeys
    (:use [keymaster.core :only [make-provider register]]))

(def socket (atom nil))

(defn bind-socket [from-client]
    (println "bound socket!")
    (reset! socket from-client))

(defn- press [ button ]
    (.send @socket button))

(defn- make-keystroke [keyname button]
    [(str "ctrl " keyname) button])

(def all-hotkeys
    (map make-keystroke
        ["SPACE" "RIGHT" "LEFT"]
        ["play" "forward" "back"]))

(defn init-hotkeys []
    (let [provider (make-provider)]
        (doseq [[hotkey button] all-hotkeys]
            (register provider
                hotkey
                (partial press button)))))
