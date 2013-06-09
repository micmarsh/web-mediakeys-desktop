(ns server-client.state
    (:use [marshmacros.coffee :only [cofmap]]
          [server-client.socket :only [press!]]
          server-client.java))


(defn- initial-values []
    "Returns initial {keystroke, listener} atoms . Make based on a file later"
    (let [buttons ["play" "back" "forward"]
          button-actions (map (fn [button] #(press! button)) buttons)
          keystrokes (map make-keystroke (map #(str "ctrl " %) ["SPACE" "RIGHT" "LEFT"]))
          listeners  (map make-action-listener  button-actions)]
          (map #(atom {:keystroke %1 :listener %2})
            keystrokes
            listeners)))

(def key-records
    (zipmap [:play :back :forward]
            (initial-values)))

(defn- update-key-record! [action record]
    (let [old-record (action key-records)]
        (reset! old-record record)))

(defn- register-all-hotkeys! []
    (doseq [[action atom] key-records]
    (let [{:keys [keystroke listener]} @atom]
        (if-not (= nil keystroke)
            (.register current-provider keystroke, listener)))))

(defn- register-in-provider! [{:keys [keystroke listener]}]
    (do (.reset current-provider)
        (register-all-hotkeys!)))

(defn set-action-to-hotkey! [action, hotkey-combination]
    (let [keystroke (make-keystroke hotkey-combination)
          listener (make-action-listener action)
          key-and-listener (cofmap keystroke listener)]
    (update-key-record! action key-and-listener)
    (register-in-provider! key-and-listener)))

(defn initialize-hotkeys! []
    (register-all-hotkeys!))


