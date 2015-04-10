#!/bin/bash

rm -rf client/
rm -rf server/

echo "Compilando..."
echo

javac *.java

echo
echo
echo "Generando Stub..."
echo

rmic ServerImp
rmic ClientImp

mkdir client
mkdir server
mkdir client/media
mkdir server/model
mkdir server/controller

cp -r media/ client/media/
cp -r model/ server/model/
cp -r controller/ server/controller/

#Servidor
mv Server.class server/
cp ClientInterface.class server/
cp ServerInterface.class server/
mv ServerImp.class server/
mv ClientImp_Stub.class server/
cp PeerInterface.class server/

#Cliente
mv *.class client/

echo " _______ FINALIZADA COMPILACION _______ "

exit 0
