#!/bin/sh
BASEDIR=$(dirname "$0")
cd $BASEDIR
javac *.java
java GameLauncher
rm -r *.class
