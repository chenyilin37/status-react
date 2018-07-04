(ns status-im.ui.screens.contacts.views
  (:require-macros [status-im.utils.views :refer [defview letsubs]])
  (:require [re-frame.core :as re-frame]
            [status-im.ui.components.react :as react]
            [status-im.i18n :as i18n]
            [status-im.ui.components.common.styles :as common.styles]
            [status-im.ui.components.list.views :as list]
            [status-im.ui.components.toolbar.view :as toolbar]
            [status-im.ui.components.contact.contact :as contact-view]
            [status-im.ui.screens.contacts.styles :as styles]
            [status-im.ui.screens.add-new.open-dapp.styles :as open-dapp.styles]))

(defn contacts-main-toolbar []
  [toolbar/toolbar {}
   nil
   [toolbar/content-title ""]
   [react/touchable-highlight
    {:on-press            #(react/alert "clicked me!")
     :accessibility-label :edit-button}
    [react/view
     [react/text {:style      common.styles/label-action-text
                  :uppercase? true}
      (i18n/label :t/edit)]]]])

(defview contacts-actionlist []
  (letsubs []
    [react/text "TODO action list"]))

(defn- render-row [row _ _]
  [contact-view/contact-view {:contact       row
                              :on-press      #(re-frame/dispatch [:start-chat (:whisper-identity %) {:navigation-replace? true}])
                              :show-forward? true}])

(defview contacts-alphabetlistview []
  (letsubs [contacts [:all-added-people-contacts]
            error-message [:new-contact-error-message]]
    [react/view
     (when (seq contacts)
       [react/text {:style open-dapp.styles/list-title}
        (i18n/label :t/contacts)])
     [list/flat-list {:data                      contacts
                      :key-fn                    :address
                      :render-fn                 render-row
                      :default-separator?        true
                      :enableEmptySections       true
                      :keyboardShouldPersistTaps :always}]]))

;;; "KAN: 联系人标签页的内容"
(defview main-view []
  (letsubs [currency [:wallet/currency]]
    (let [binding ""]
      [react/view styles/container
       [contacts-main-toolbar]
       [contacts-actionlist]
       [contacts-alphabetlistview]])))

