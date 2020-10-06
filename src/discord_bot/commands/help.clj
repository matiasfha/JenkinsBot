(ns discord-bot.commands.help)


(defn help-message [channel-id]
  (format "Hello there, Human!

          You have summoned me. Let's see about getting you what you need.
          
 					? Need technical help?
          => Post in the <#%s> channel and other humans will assist you.
          
          ? Looking for the Code of Conduct?
          => Here it is: <https://opensource.facebook.com/code-of-conduct> 
          
          ? Something wrong?
          => You can flag an admin with @admin
          
          I hope that resolves your issue!
          -- Helpbot
          
          " channel-id))

