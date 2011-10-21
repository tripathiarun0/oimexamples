setlocal

set UDNAME=RESC
set RESOURCENAME=Resource C
set PROCESSNAME=Process C


sed -e "s/UDNAME/%UDNAME%/g" templateresource.xml >newud.xml
sed -e "s/RESOURCENAME/%RESOURCENAME%/g" newud.xml >newres.xml
sed -e "s/PROCESSNAME/%PROCESSNAME%/g" newres.xml >%UDNAME%.xml

rm newud.xml
rm newres.xml

endlocal
