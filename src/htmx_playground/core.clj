(ns htmx-playground.core
  (:require [hiccup.core :refer [html]]
            [ring.middleware.file :refer [wrap-file]]
            [ring.logger :as logger]))

;; Demo data set
(def cards [{:title "Wash windows" :content "they are really dirty" :status :start}
            {:title "prep food" :content "Kids go hungry" :status :done}
            {:title "go swimming" :content "fish feel so alone" :status :done}])

;; Page with everything that has to be on it
(defn page [body]
  (html
   [:header 
    [:link {:rel "stylesheet" :href "/css/style.css"}]
    [:script {:src "https://unpkg.com/htmx.org@1.8.6"}]
    [:title "Kanban Style!"]]
   [:div.header "Kanban Style!"]
   [:div.topnav [:button {:data-hx-get "/column" 
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

(defn board []
  [:div.row
   (column "start" (filter #(= :start (:status %)) cards))
   (column "done" (filter #(= :done (:status %)) cards))])

(defn handler [req]
  (let [path (:uri req)
        resp (case path
               "/test" (html [:h1.test {:hx-post "/click"} "Testing Mars"])
               "/board" (page (board))
               "/column" (html (column "new" cards))
               "404!")]
    {:status 200
     :body resp}))

(def app 
  (logger/wrap-with-logger 
   (wrap-file handler "resources/public")))
