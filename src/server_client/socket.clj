(ns server-client.socket)

(def socket (atom nil))

(defn bind-socket! [new-socket]
    (reset! socket new-socket))

(defn press! [button]
    (do (println button @socket)
        (.send @socket button)))
