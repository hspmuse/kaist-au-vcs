#!/bin/bash
temp1=$(ls -la *.rpm | tail -1)
temp2=${temp1: -16}
presentVer=${temp2:0:1}
echo $temp1
echo $temp2
echo $presentVer
upgradeVer=$(curl http://10.1.3.75:8080/update/check/$presentVer)
#upgradeVer=1
echo $upgradeVer
if [ $presentVer -eq $upgradeVer ]; then
    echo "not exist upgrade version!!"
    exit
fi 
head="org.tizen.voice-control-panel-0."
tail=".1-1.armv7l.rpm"
upgradefilename+=$head$upgradeVer$tail
echo $upgradefilename
curl http://10.1.3.75:8080/update/download/$upgradeVer --output ./$upgradefilename
rpm -Uvh --force /home/owner/$upgradefilename

voiceprocess=$(pgrep -f voice)
echo "========================================"
echo $voiceprocess
echo "========================================"

kill -9 $voiceprocess

launch_app org.tizen.voice-control-panel

