(ns server-client.hotkeys)
(import [com.tulskiy.keymaster.common HotKey MediaKey Provider HotKeyListener])
(import [javax.swing KeyStroke])

(def socket (atom nil))

(defn bind-socket [from-client]
    (println "bound socket!")
    (reset! socket from-client))

(defn- press [ button ]
    (.send @socket button))

(defn- get-listener [button]
   (proxy [HotKeyListener] []
            (onHotKey [event]
                (press button))))

(defn- make-keystroke [keyname button]
    [(KeyStroke/getKeyStroke (str "ctrl " keyname))
    button])

(def all-hotkeys
    (map make-keystroke
        ["SPACE" "RIGHT" "LEFT"]
        ["play" "forward" "back"]))

(defn init-hotkeys []
    (let [provider (Provider/getCurrentProvider  false)]
        (doseq [[hotkey button] all-hotkeys]
            (.register provider
                hotkey
                (get-listener button)))))
