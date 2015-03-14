#!/bin/bash

rm -r client/
rm -r server/

mkdir client
mkdir server

echo "Compilando..."
echo 

javac *.java

echo
echo
echo "Genrando Stub..."
echo

rmic ServerImpl
rmic ClientImpl

#Cliente
mv Client.class client/
cp ClientInterface.class client/
cp ServerInterface.class client/
mv ClientImpl.class client/
mv ServerImpl_Stub.class client/

#Servidor
mv Server.class server/
mv ClientInterface.class server/
mv ServerInterface.class server/
mv ServerImpl.class server/
mv ClientImpl_Stub.class server/

echo " ---------- COMPILADO -----------"
exit 0
