(ns kanban-style.core
  (:require [hiccup.core :refer [html]]
            [ring.middleware.file :refer [wrap-file]]
            [ring.util.response :refer [response redirect not-found]]
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
  (let [path (:uri req)]
    (case path
      "/" (redirect "/board")
      "/test" (response (html [:h1.test {:hx-post "/click"} "Testing Mars"]))
      "/board" (response (page (board (wcar my-wcar-opts (car/get "cards")))))
      "/column" (response (html (column "new" cards)))
      "/ping" (response (html (wcar my-wcar-opts (car/ping))))
      "/populate" (response (html (wcar my-wcar-opts (car/set "cards" cards))))
      (not-found (str "Could not find " path)))))

(def app 
  (-> handler
      (wrap-file "resources/public")
      (logger/wrap-with-logger)))
