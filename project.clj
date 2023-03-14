(defproject htmx-playground "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [hiccup "1.0.5"]
                 [ring-logger "1.1.1"]]
  :plugins [[lein-ring "0.12.6"]]
  
  :ring {:handler htmx-playground.core/app
         :auto-refresh? true}
  :repl-options {:init-ns htmx-playground.core})
