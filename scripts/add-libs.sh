#!/bin/bash

add_js(){
  echo "installing: " $1

  npm i $1 --save
  re-natal use-component $1
}

add_js react-native-swiper
add_js react-native-swipe-list-view
add_js react-native-action-button

# re-natal use-figwheel