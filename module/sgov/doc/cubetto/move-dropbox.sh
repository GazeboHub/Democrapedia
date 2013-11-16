#!/bin/sh

APPSDIR="$HOME/Dropbox/Apps"

APP="Cubetto BPMN"

CUBPROJ="Democrapedia"

find "${APPSDIR}/${APP}/${CUBPROJ}" -type f -print0 |
  xargs -0 -I "{}" mv {} .
  
