(ns ring-app.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn handler [request-map]
  (response/response
    (str "<html><body>Your IP is: "
         (:remote-addr request-map)
         "</body></html>")))

(defn wrap-nocache [handler]
  (fn [request]
    (-> request
        handler
        (assoc-in [:headers "Pragme"] "no-cache"))))

(defn -main []
  (jetty/run-jetty
    (-> #'handler wrap-nocache wrap-reload)
    {:port 3000
     :join? false}))
