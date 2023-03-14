(ns htmx-playground.core
  (:require [hiccup.core :refer [html]]
            [ring.middleware.file :refer [wrap-file]]
            [ring.logger :as logger]
            [taoensso.carmine :as car :refer [wcar]]))


(defonce my-conn-pool   (car/connection-pool {}))
(def     my-conn-spec-1 {:uri "redis://localhost:6379/"})

(def my-wcar-opts
  {:pool my-conn-pool
   :spec my-conn-spec-1})

;; Demo data set
(def cards [{:title "Wash windows" :content "they are really dirty" :status :start}
            {:title "prep food" :content "Kids go hungry" :status :start}
            {:title "go swimming" :content "fish feel so alone" :status :done}])


;; Page with everything that has to be on it
(defn page [body]
  (html
   [:header 
    [:link {:rel "stylesheet" :href "/css/style.css"}]
    [:script {:src "https://unpkg.com/htmx.org@1.8.6"}]
    [:title "Kanban Style!"]]
   [:div.header "Kanban Style!"]
   [:div.topnav
    [:button {:data-hx-get "/ping" } "Ping"]
    [:button {:data-hx-get "/column"
              :data-hx-target ".row"
              :data-hx-swap "afterend"} "Add"]]
   [:body body]))

(defn card [{content :content title :title}]
  [:div.card 
   [:div.title title]
   [:div.content content]])

(defn column [title cards]
  [:div.column title
   (for [c cards]
     (card c))
   [:form {:action "/card" :method "POST"}
    [:input.title]
    [:input.content]
    [:button {:data-hx-post "/card"} "+"]]])

(defn board [cards]
  [:div.row
   (column "start" (filter #(= :start (:status %)) cards))
   (column "done" (cons (card {:content "Add" :title "Add"}) 
                    (filter #(= :done (:status %)) cards) ))])

(defn handler [req]
  (let [path (:uri req)
        resp (case path
               "/test" (html [:h1.test {:hx-post "/click"} "Testing Mars"])
               "/board" (page (board (wcar my-wcar-opts (car/get "cards"))))
               "/column" (html (column "new" cards))
               "/ping" (html (wcar my-wcar-opts (car/ping)))
               "/populate" (html (wcar my-wcar-opts (car/set "cards" cards)))
               "404!")]
    {:status 200
     :body resp}))

(def app 
  (logger/wrap-with-logger 
   (wrap-file handler "resources/public")))
