(defproject ducttest "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0-RC2"]
                 [duct/core "0.6.1"]
                 [duct/module.logging "0.3.1"]
                 [duct/logger "0.2.1"]
                 [duct/module.web "0.6.3"]
                 [duct/module.ataraxy "0.2.0"]
                 [com.datomic/datomic-pro "0.9.5561.62"]
                 [json-html "0.4.4"]
                 [hiccup "1.0.5"]
                 [io.rkn/conformity "0.5.1"]]
  :plugins [[duct/lein-duct "0.10.5"]]
  :main ^:skip-aot ducttest.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.2.0"]
                                   [eftest "0.4.0"]
                                   [kerodon "0.9.0"]]}})
