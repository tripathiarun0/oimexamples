#!/bin/sh

UDNAME=$1
RESOURCENAME=$2
PROCESSNAME=$3


sed -e "s/UDNAME/$UDNAME/g" templateresource.xml >newud.xml
sed -e "s/RESOURCENAME/$RESOURCENAME/g" newud.xml >newres.xml
sed -e "s/PROCESSNAME/$PROCESSNAME/g" newres.xml >$UDNAME.xml

rm newud.xml
rm newres.xml
