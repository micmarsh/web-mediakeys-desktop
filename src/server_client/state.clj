(ns server-client.state
    (:use [marshmacros.coffee :only [cofmap]]))
(import [com.tulskiy.keymaster.common Provider HotKeyListener])
(import [javax.swing KeyStroke])

(def socket (atom nil))

(defn bind-socket! [socket]
    (println "lulz"))

(defn- press [ button ]
    (.send @socket button))
;^should be in socket namespace

(defn- make-action-listener [button]
   (proxy [HotKeyListener] []
            (onHotKey [event]
                (press button))))

(defn- make-keystroke [keystroke]
    (KeyStroke/getKeyStroke keystroke))

(def current-provider
    (Provider/getCurrentProvider false))

(defn- initial-values []
    "Returns initial {keystroke, listener} atoms . Make based on a file later"
    (let [keystrokes (map make-keystroke (map #(str "ctrl " %) ["SPACE" "RIGHT" "LEFT"]))
          listeners  (map make-action-listener ["play" "back" "forward"])]
          (map #(atom {:keystroke %1 :listener %2})
            keystrokes
            listeners)))

(def key-records
    (zipmap [:play :back :forward]
            (initial-values)))

(defn update-key-record! [action record]
    (let [old-record (action key-records)]
        (reset! old-record record)))

(defn register-all-hotkeys! []
    (doseq [[action atom] key-records]
    (let [{:keys [keystroke listener]} @atom]
        (if-not (= nil keystroke)
            (.register current-provider keystroke, listener)))))

(defn register-in-provider! [{:keys [keystroke listener]}]
    (do (.reset current-provider)
        (register-all-hotkeys!)))

(defn set-action-to-hotkey! [action, hotkey-combination]
    (let [keystroke (make-keystroke hotkey-combination)
          listener (make-action-listener action)
          key-and-listener (cofmap keystroke listener)]
    (update-key-record! action key-and-listener)
    (register-in-provider! key-and-listener)))

(register-all-hotkeys!)
