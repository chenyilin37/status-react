(ns status-im.ui.components.react-exts
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]))


;;;;;; react-native-swiper
(def Swiper-JS (js/require "react-native-swiper"))

(assert Swiper-JS)

(def swiper (r/adapt-react-class Swiper-JS))


;;;;;; react-native-swipe-list-view
(def Swipe-Listview-JS (js/require "react-native-swipe-list-view"))

(assert Swipe-Listview-JS)

(def swipe-list-view (r/adapt-react-class (.-SwipeListView Swipe-Listview-JS)))
(def swipe-list-row (r/adapt-react-class (.-SwipeRow Swipe-Listview-JS)))


;;;; react-native-action-button
(def ActionButton_JS (js/require "react-native-action-button"))
(def action-button (r/adapt-react-class (.-default ActionButton_JS)))
