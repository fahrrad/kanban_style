(defproject htmx-playground "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring "1.9.6"]
                 [hiccup "1.0.5"]
                 [ring-logger "1.1.1"]
                 [com.taoensso/carmine "3.2.0"]
                 ;; https://mvnrepository.com/artifact/org.slf4j/slf4j-api
                 [org.slf4j/slf4j-api "2.0.6"]
                 ;; https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
                 [ch.qos.logback/logback-classic "1.4.5"] 
                 [ch.qos.logback.contrib/logback-jackson "0.1.5"]
                 [ch.qos.logback.contrib/logback-json-classic "0.1.5"]
                 [com.fasterxml.jackson.core/jackson-databind "2.14.2"]
                 [org.clojure/tools.logging "1.2.4"]]
  :jvm-opts ["-Dclojure.tools.logging.factory=clojure.tools.logging.impl/slf4j-factory"]
  :plugins [[lein-ring "0.12.6"]] 
  :ring {:handler htmx-playground.core/app}
  :profiles {:dev {:resource-paths ["resources" "resources.dev"]}
             :prod {:resource-paths ["resources" "resources.prod"]}}
  :repl-options {:init-ns htmx-playground.core})
