(ns server-client.java)
(import [com.tulskiy.keymaster.common Provider HotKeyListener])
(import [javax.swing KeyStroke])

;TODO: look up some types from original library, and add
;some type hints here, fuck yeah it'll be a party

(defn ^HotKeyListener make-action-listener [action]
   (proxy [HotKeyListener] []
            (onHotKey [event] do (
                (println "woaaah")
                (action)))))
            ;this shit's throwing exceptions but not breaking wtf
            ;maybe wrap the function in some exception handling
            ;action before it gets here. yeeeeaaaah.

(defn make-keystroke [keystroke]
    (KeyStroke/getKeyStroke keystroke))

(def ^Provider current-provider
    (Provider/getCurrentProvider false))