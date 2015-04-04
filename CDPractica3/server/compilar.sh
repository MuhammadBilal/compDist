#!/bin/bash

rm -r client/
rm -r server/

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

cp -r media/ client/
cp -r model/ server/
cp -r controller/ server/

#Servidor
mv Server.class server/
cp ClientInterface.class server/
cp ServerInterface.class server/
mv ServerImp.class server/
mv ClientImp_Stub.class server/

#Cliente
mv *.class client/

echo " _______ FINALIZADA COMPILACION _______ "

exit 0
