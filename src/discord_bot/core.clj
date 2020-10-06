(ns discord-bot.core
  (:require [clojure.edn :as edn]
            [clojure.core.async    :as a]
            [discljord.connections :as c]
            [discljord.messaging   :as m]
            [discljord.events :as e]
            ))
(use '[discord-bot.commands.help :as help])

(def read-config (edn/read-string (slurp "config.edn")))

(def token (:token read-config))

(def state (atom nil))


(defn help-handler
  [event-type {{bot :bot} :author :keys [channel-id content ]}]
  (if (= content "!ayuda")
    (m/create-message! (:messaging @state) channel-id :content (help/help-message channel-id))
    )
  )


(def handlers {:message-create [#'help-handler]})


(defn -main [& args]                                                       
  (let [event-ch (a/chan 100)                                              
      connection-ch (c/connect-bot! token event-ch)                        
      messaging-ch (m/start-connection! token)                             
      init-state {:connection connection-ch                                
                  :event event-ch                                          
                  :messaging messaging-ch}]                                
  (reset! state init-state)                                                
  (try (e/message-pump! event-ch (partial e/dispatch-handlers #'handlers)) 
    (finally                                                               
      (m/stop-connection! messaging-ch)                                    
      (c/disconnect-bot! connection-ch))))                                 
)                                                                          


