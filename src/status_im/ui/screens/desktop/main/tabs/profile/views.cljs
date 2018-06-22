(ns status-im.ui.screens.desktop.main.tabs.profile.views
  (:require-macros [status-im.utils.views :as views])
  (:require [re-frame.core :as re-frame]
            [status-im.ui.components.react :as react]
            [status-im.ui.screens.profile.user.views :as profile]
            [status-im.utils.build :as build]
            [status-im.ui.components.colors :as colors]
            [status-im.i18n :as i18n]
            [status-im.ui.components.icons.vector-icons :as vector-icons]
            [clojure.string :as string]
            [status-im.ui.components.qr-code-viewer.views :as qr-code-viewer]
            [status-im.ui.screens.desktop.main.tabs.profile.styles :as styles]
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

(views/defview qr-code []
  (views/letsubs [current-account [:get-current-account]]
    (let [public-key (:public-key current-account)]
      [react/view {:style styles/qr-code-container}
       [react/text {:style styles/qr-code-title}
        (string/replace (i18n/label :qr-code-public-key-hint) "\n" "")]
       [react/view {:style styles/qr-code}
        [qr-code-viewer/qr-code {:value public-key :size 130} #_{:style styles/qr-code}]]
       [react/text {:style styles/qr-code-text}
        public-key]
       [react/touchable-highlight {:on-press #(react/copy-to-clipboard public-key)}
        [react/view {:style styles/qr-code-copy}
         [react/text {:style styles/qr-code-copy-text}
          (i18n/label :copy-qr)]]]])))

(defn share-contact-code []
  [react/touchable-highlight {:on-press #(re-frame/dispatch [:navigate-to :qr-code])}
   [react/view {:style styles/share-contact-code}
    [react/view {:style styles/share-contact-code-text-container}
     [react/text {:style       styles/share-contact-code-text}
      (i18n/label :share-contact-code)]]
    [react/view {:style               styles/share-contact-icon-container
                 :accessibility-label :share-my-contact-code-button}
     [vector-icons/icon :icons/qr {:style {:tint-color colors/blue}}]] ]])

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
      [share-contact-code]
      #_[my-profile-info current-account]]
     [react/view {:style {:height 1 :background-color "#e8ebec" :margin-horizontal 16}}]
     [react/touchable-highlight {:on-press #(re-frame/dispatch [:logout])
                                 :style {:margin-top 60}}

      [react/view {:style styles/logout-row}
       [react/text {:style {:color colors/red}} (i18n/label :t/logout)]
       [react/text {:style {:color colors/gray}} "V" build/version]]]]))
