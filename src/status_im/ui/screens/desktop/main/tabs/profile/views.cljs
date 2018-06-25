(ns status-im.ui.screens.desktop.main.tabs.profile.views
  (:require-macros [status-im.utils.views :as views])
  (:require [re-frame.core :as re-frame]
            [status-im.ui.components.react :as react]
            [status-im.ui.screens.profile.user.views :as profile]
            [status-im.utils.build :as build]
            [status-im.ui.components.colors :as colors]
            [status-im.i18n :as i18n]
            [status-im.ui.components.icons.vector-icons :as vector-icons]
            [status-im.ui.components.qr-code-viewer.views :as qr-code-viewer]
            [status-im.ui.screens.profile.user.styles :as styles]
            [status-im.ui.screens.profile.user.views :as profile]))

(defn profile-badge [{:keys [name]}]
  [react/view {:margin-vertical 10}
   [react/text {:style {:font-weight :bold}
                :number-of-lines 1}
    name]])

(defn profile-info-item [{:keys [label value]}]
  [react/view
   [react/view
    [react/text
     label]
    [react/view {:height 10}]
    [react/touchable-opacity {:on-press #(react/copy-to-clipboard value)}
    [react/text {:number-of-lines 1
                 :ellipsizeMode   :middle}
     value]]]])

(defn- show-qr [contact source value]
  [react/modal "Some text"]
  #_(re-frame/dispatch [:navigate-to :profile-qr-viewer {:contact contact
                                                        :source  source
                                                        :value   value}]))

(defn share-contact-code [{:keys [public-key] :as current-account}]
  [react/view
   [react/touchable-highlight {:on-press #(show-qr current-account :public-key public-key) 
                               :style {:background-color "#4360df" :opacity 0.1}}
    [react/view (merge styles/share-contact-code {:opacity 1.0})
     [react/view styles/share-contact-code-text-container
      [react/text {:style       styles/share-contact-code-text
                   :uppercase? true}
       "Share my contact code"]]
     [react/view {:style               styles/share-contact-icon-container
                  :accessibility-label :share-my-contact-code-button}
      [vector-icons/icon :icons/qr {:color colors/blue}]]]]
   [qr-code-viewer/qr-code-viewer {:style styles/qr-code}
    public-key "Share this code to start chatting" public-key]])

(defn my-profile-info [{:keys [public-key]}]
  [react/view
   [profile-info-item
    {:label "Contact Key"
     :value public-key}]])

(views/defview profile []
  (views/letsubs [current-account [:get-current-account]]
    [react/view {:margin-top 40 :margin-horizontal 10}
     [react/view
      [profile-badge current-account]]
     [react/view {:style {:height 1 :background-color "#e8ebec" :margin-horizontal 16}}]
     [react/view
      [share-contact-code current-account]
      #_[my-profile-info current-account]]
     [react/view {:style {:height 1 :background-color "#e8ebec" :margin-horizontal 16}}]
     [react/touchable-highlight {:on-press #(re-frame/dispatch [:logout])
                                 :style {:margin-top 60}}

      [react/view {:style styles/logout-row}
       [react/text {:style {:color colors/red}} (i18n/label :t/logout)]
       [react/text {:style {:color colors/gray}} "V" build/version]]]]))
