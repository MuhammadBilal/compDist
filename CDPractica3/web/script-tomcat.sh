#!/bin/bash

clear
pwd
echo
echo ">> Stoping Tomcat..."
/Library/Tomcat/bin/shutdown.sh 1>script.log

echo ">> Compiling Java classes..."
cd WEB-INF/classes/ # entro para que reconozca los paquetes
javac -cp ../../WEB-INF/lib/servlet-api.jar:../../WEB-INF/lib/mysql-connector-java-5.1.34-bin.jar controller/*.java model/*.java
cd ../../ # vuelvo a root

echo ">> Creating WAR file..."
jar cvf meteochat.war *.html *.jsp img WEB-INF/lib WEB-INF/web.xml WEB-INF/classes/controller/*.class WEB-INF/classes/model/*.class 1>script.log

echo ">> Deleting previous servlets..."
rm -rf /Library/Tomcat/webapps/meteochat* 1>script.log

echo ">> Installing servlets..."
mv meteochat.war /Library/Tomcat/webapps/ 1>script.log

echo ">> Restarting Tomcat..."
/Library/Tomcat/bin/startup.sh 1>script.log

echo "Done! :P <- You can check logs in script.log"
