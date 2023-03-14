(ns htmx-playground.core
  (:require [hiccup.core :refer [html]]
            [ring.middleware.file :refer [wrap-file]]))

(def cards [{:title "Wash windows" :content "they are really dirty" :status :start} 
            {:title "prep food" :content "Kids go hungry" :status :done} 
            {:title "go swimming" :content "fish feel so alone" :status :done}])

(defn card [{content :content title :title}]
  [:div.card 
   [:div.title title]
   [:div.content content]])

(defn board []
  (html
   [:div.header "Kanban style"]
   [:div.topnav [:a {:href "/test"} "navigation"]]
   [:div.row
    [:div.column "Done"
     (for [c (filter #(= :done (:status %)) cards)]
       (card c))]
    [:div.column "Start"
     (for [c (filter #(= :start (:status %)) cards)]
       (card c))]]))

(defn handler [req]
  (let [path (:uri req)
        resp (case path
               "/test" (html [:h1.test {:hx-post "/click"} "Testing Mars"])
               "/board" (board)
               "404!")]
    {:status 200
     :body (html
            [:header
             [:title " Testing hmlx"]
             [:link {:rel "stylesheet" :href "/css/style.css"}]
             [:script {:src "https://unpkg.com/htmx.org@1.8.6"}]]
            [:body resp])}))

(def app 
   (wrap-file handler "resources/public")))
