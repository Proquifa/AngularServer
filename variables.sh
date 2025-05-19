#!/bin/sh
# Inicializacion de variables
# Inputs -> M2_HOME, JAVA_HOME

export M3_HOME=/usr/local/opt/maven@3.2
#JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_201.jdk/Contents/Home
export PATH=$PATH:$M3_HOME/bin:$JAVA_HOME
#export GLASSFISH_HOME=/usr/local/opt/glassfish/libexec


