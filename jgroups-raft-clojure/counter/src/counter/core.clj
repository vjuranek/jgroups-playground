(ns counter.core
  (:import
    (org.jgroups JChannel)
    (org.jgroups.blocks.atomic Counter)
    (org.jgroups.raft.blocks CounterService)))


(defn jchannel
  [props, name]
  (-> (JChannel. props) (.name name)))

(defn counter
  [jch]
  (-> (CounterService. jch) (.raftId (.name jch)) (.replTimeout 5000) (.getOrCreateCounter "counter" 0)))

(defn join-channel
  [jch]
  (.connect jch "counters"))

(defn start-counter
  [props, name]
  (let [jch (jchannel props name),
        cnt (counter jch)]
    (join-channel jch)
    cnt))

(defn run
  []
  (let [cont (atom true),
        cnt (start-counter "raft.xml" "A")]
    (while @cont
      (do
        (println "[1] Increment")
        (println "[e] Exit")
        (let [k (read-line)]
          (case k
            "1" (println (.incrementAndGet cnt))
            "e" (reset! cont false)
            (println "write 1 or e")))))))

(defn -main
  [& args]
  (do
    (println "JGroups Raft counter")
    (run)))
