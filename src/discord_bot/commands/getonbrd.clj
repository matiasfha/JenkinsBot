(ns discord-bot.commands.getonbrd
  (:require [chesire.core :as json]
            [clj-http.client :as client])
  )

; attributes.title
; attributes.remote?
; attributes.modality
; attributes.published_at
; attributes.company.data.attributes.name
; attributes.company.data.attributes.web
; attributes.links.public_url

(defn get-items [response]
  (:data (json/parse-string (:body response) true))
)
(defn select-keys* [m paths]
  (into {} (map (fn [p]
                  [(last p) (get-in m p)]))
        paths))

(defn parse [item]
  (println item)
  )

(defn parse-items [items]
  (map println items)
  )
; @TODO parse/map the data map
(defn get-on-brd-message [content]
    (let [url "https://sandbox.getonbrd.dev/api/v0/search/jobs?query=Javascript&per_page=1&expand=[\"company\"]"]
      (client/get url {:accept :json :async? true }
                  ;; respond callback
                  (fn [response] (let [items (get-items response)]
                                  (map println items)
                                  )
                    )
                  ;; raise callback
                  (fn [exception] (println (.getMessage exception)))
                  )
      )
    )

(defn get-on-brd-handler
  [event-type {{bot :bot} :author :keys [channel-id content]}]
  (let [query (second (re-matches #"!trabajo (.*)" content))]
    (when query
      (println query)
                                        ; perform the api request and post
    (m/create-message! (:messaging @state) channel-id :content (get-on-brd-message query))

    )
  )
)
