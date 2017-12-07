(ns ducttest.handler.example
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response] 
            [clojure.java.io :as io]
            [integrant.core :as ig]
            [datomic.api :as d]
            [io.rkn.conformity :as c]
            [duct.logger :as l]
            [hiccup.core :as h]
            [json-html.core :as jh]))

(defmethod ig/init-key :ducttest.handler/example [_ {:keys [db-conn] :as options}]
  (fn [{[_] :ataraxy/result}]
    (let [db (d/db db-conn)]
      [::response/ok
       (h/html [:ul
                (map (fn [x] [:li (jh/edn->html x)]) (d/pull-many db '[*] (map first (d/q '[:find ?c :where [?c :community/name]] db))))])])))

(defmethod ig/init-key :ducttest.handler/main-db
  [_ {:keys [uri schema data log] :as options}]
  (when (d/create-database uri)
    (l/log log :info ::datomic-conn {:options options}))
  (let [conn (d/connect uri)]
    (l/log log :info ::datomic-conn "Conform Schema")
    (clojure.pprint/pprint (c/ensure-conforms conn (c/read-resource schema)))
    (l/log log :info ::datomic-conn "Conform Data")
    (clojure.pprint/pprint (c/ensure-conforms conn (c/read-resource data)))
    conn))
